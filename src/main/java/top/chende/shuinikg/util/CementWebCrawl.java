package top.chende.shuinikg.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.chende.shuinikg.constant.CementCrawlConstant;
import top.chende.shuinikg.crawl.demo.Login;
import top.chende.shuinikg.model.CementPage;
import top.chende.shuinikg.repository.CementPageRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: chende
 * @date: 2019/12/3 10:50
 * @description: 爬取线程
 */
public class CementWebCrawl implements  Runnable{

    Logger logger = LoggerFactory.getLogger(CementWebCrawl.class);


   // @Autowired
    RedisTemplate redisTemplate;
   // @Autowired
    CementPageRepository cementPageRepository;

    private volatile boolean flag=true;

    WebDriver driver=new ChromeDriver();
    String login="https://i.ccement.com/pc/login.html?refurl=http://www.ccement.com/";

    public CementWebCrawl(){}
    public CementWebCrawl(RedisTemplate redisTemplate, CementPageRepository cementPageRepository){

        this.cementPageRepository=cementPageRepository;
        this.redisTemplate=redisTemplate;
    }


    public void init(){
        logger.info("CementWebCrawl  init ");
        cementLogin();
        this.flag=true;
        new Thread(this).start();
    }

    public void cementLogin(){
        driver.get(login);
        Login l=new Login();
        l.setUserName(driver,"chende123");
        l.setPassWord(driver,"chende54321");
        l.clickLogin(driver);
        redisTemplate.opsForList().rightPush(CementCrawlConstant.REDIS_URL_KEY,driver.getCurrentUrl());

    }

    public  void  stopCrawl(){
        this.flag=false;
        logger.info("CementWebCrawl  stopCrawl ");
    }

    public void reStart(){
        this.flag=true;
        this.driver=new ChromeDriver();
        this.cementLogin();
        new Thread(this).start();
        logger.info("CementWebCrawl  reStart ");

    }
    //彻底关闭爬虫
    public void shutdownCrawl(){
        stopCrawl();
        redisTemplate.delete(CementCrawlConstant.REDIS_URL_KEY);
        redisTemplate.delete(CementCrawlConstant.REDIS_VISTID_URL_KEY);

    }

    @Override
    public void run() {
        List<CementPage> pageList=new ArrayList<>();
        while (flag){
            String url=(String) redisTemplate.opsForList().leftPop(CementCrawlConstant.REDIS_URL_KEY);
            if (redisTemplate.opsForSet().isMember(CementCrawlConstant.REDIS_VISTID_URL_KEY,url)) {

                continue;
            }
            driver.get(url);

            //存到数据库 或者放到队列
            CementPage cementPage=new CementPage();
            cementPage.setUrl(url);
            cementPage.setHtml(driver.getPageSource());
            pageList.add(cementPage);
            if (pageList.size()==CementCrawlConstant.NUM_STORE_DB) {
                cementPageRepository.saveAll(pageList);
                cementPageRepository.flush();
                pageList.clear();
            }
            redisTemplate.opsForSet().add(CementCrawlConstant.REDIS_VISTID_URL_KEY,url);

            //解析url
            Document document= Jsoup.parse(driver.getPageSource());
            //List<WebElement> elements =driver.findElements(By.tagName("a"));
            Elements elements=document.getElementsByTag("a");
            Iterator<Element> iterable=elements.iterator();
            while(iterable.hasNext()){
                Element e=iterable.next();
                if (e.outerHtml().contains("href")){
                    String href= e.attr("href");
                    if (isHttpUrl(href) && href.contains("cement.com") ){
                        redisTemplate.opsForList().rightPush(CementCrawlConstant.REDIS_URL_KEY,href);
                    }
                }
            }
//            for (WebElement e:elements){
//                if (e.().contains("href")){
//                    String href=e.getAttribute("href");
//                    if (isHttpUrl(href) && href.contains("cement.com") ){
//                        redisTemplate.opsForList().rightPush(CementCrawlConstant.REDIS_URL_KEY,href);
//                    }
//                }
//
//            }
            logger.info("队列里还有{}条url",redisTemplate.opsForList().size(CementCrawlConstant.REDIS_URL_KEY));

        }
        //线程停止后
      //  redisTemplate
    }

    /**
     * 判断字符串是否为URL
     * @param urls 需要判断的String类型url
     * @return true:是URL；false:不是URL
     */
    public  boolean isHttpUrl(String urls) {
        if (urls==null || urls.equals("")) return false;
        boolean isurl = false;
        Pattern pat = Pattern.compile(CementCrawlConstant.URL_MATCH_REGEX.trim());//对比
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }


}
