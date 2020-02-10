package top.chende.shuinikg.crawl.cement;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author: chende
 * @date: 2020/1/13 10:09
 * @description: 水泥网爬虫
 *
 *   /Users/chende/Desktop/data-new
 */
public class CcementCrawl {

    // http://www.ccement.com/news/search/keyword/熟料/?page=1
    static  String urltemplate="http://www.ccement.com/news/search/keyword/熟料/?page=";




    static String urltemplate2="http://www.ccement.com/news/search/keyword/烧成/?page=";
    /**
     * 搜索关键词烧成  66页结果
     * 搜索关键词 总共236结果
     */
    static int tatalPage=66;  //66  236

    public static void main(String[] args) {
        String u;
        int count=780; //
        for(int i=1;i<=tatalPage;i++){
            u=urltemplate+i;
           // System.out.println(u);
            try {
                Document document =  Jsoup.connect(u)
                        .timeout(10*1000)
                        .get();
                Elements elements=document.getElementsByClass("inews_item");
                Iterator<Element> it=elements.iterator();

                while (it.hasNext()){
                    Element e=it.next();
                    count++;
                  //  System.out.println(count+":  "+e.text());
                    System.out.println(e.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
