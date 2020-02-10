package top.chende.shuinikg.crawl.cement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author: chende
 * @date: 2020/1/13 14:34
 * @description: 爬取武汉理工大学图书馆 搜索的结果
 */
public class WHUTLibCrawl {
    static String url="http://ss.zhizhen.com/s?sw=水泥熟料热耗&size=15&isort=0&x=0_467&pages=2";

    //异步加载的！

    static  WebDriver driver=new ChromeDriver();

    public static String getUrlContent(String url){
        driver.get(url);
        Document document= Jsoup.parse(driver.getPageSource());
        System.out.println(document.outerHtml());
        Elements elements=document.getElementsByClass("savelist_con");
        Iterator<Element> it=elements.iterator();

        while (it.hasNext()){
            Element e=it.next();
            System.out.println(e.text());
        }
        driver.close();
        driver.quit();
        return null;
    }


    public static void main(String[] args) {
        getUrlContent(url);

    }
}
