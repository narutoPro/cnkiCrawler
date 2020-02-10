package top.chende.shuinikg.crawl.cement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author: chende
 * @date: 2020/1/13 11:52
 * @description: 中国水泥协会 爬虫
 *
 * http://www.dcement.com/chinacca/wenjian/
 * http://www.dcement.com/chinacca/news/
 * http://www.dcement.com/chinacca/zhengce/
 */
public class CementAssociation {
    static  String url1="http://www.dcement.com/chinacca/wenjian/";
    static  String url2="http://www.dcement.com/chinacca/news/";
    static  String url3="http://www.dcement.com/chinacca/zhengce/";



    static int tatalPage=50;

    public static void main(String[] args) {
        String u;
        int count=0;

            u=url3;
            // System.out.println(u);
            try {
                Document document =  Jsoup.connect(u)
                        .timeout(10*1000)
                        .get();
                Element searchList=document.getElementsByClass("textcontent").first();
                document.getElementById("id");
                Elements elements=  searchList.getElementsByTag("li");

                Iterator<Element> it=elements.iterator();

                while (it.hasNext()){
                    Element e=it.next();

                    String c=e.attr("class");

                    if (c!=null &&"split".equals(c))
                        continue;
                    count++;
                    String articleTitle=e.getElementsByTag("a").text();
                    e.html();
                    e.outerHtml();
                     // System.out.println(count+":  "+articleTitle);
                    System.out.println(articleTitle);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



}
