package top.chende.shuinikg.crawl.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Iterator;

/**
 * @author: chende
 * @date: 2019/12/2 23:07
 * @description:
 */
public class WebDriverDemo {
    static  String startUrl="http://www.ccement.com/";
    static  String login="https://i.ccement.com/pc/login.html?refurl=http://www.ccement.com/";
    public static void main(String[] args) {

        ChromeOptions options=new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver=new ChromeDriver(options);
        driver.get(login);

        Login l=new Login();
        l.setUserName(driver,"chende123");
        l.setPassWord(driver,"chende54321");
        l.clickLogin(driver);

        WebElement element=driver.findElement(By.tagName("body"));

    //    System.out.println(driver.getPageSource());
        Document document= Jsoup.parse(driver.getPageSource());
        Elements elements=document.getElementsByTag("a");
        Iterator<Element> iterable=elements.iterator();
        while(iterable.hasNext()){
            Element e=iterable.next();
            System.out.println("html---->"+e.html());
            System.out.println("outerHtml------> "+e.outerHtml());
            System.out.println("href  ---->"+e.attr("href"));
//            if (e.html().contains("href")){
//                String href= e.attr("href");
//                System.out.println(href);
//            }
        }
        try {
            Thread.sleep(15*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
        driver.quit();
    }
}
