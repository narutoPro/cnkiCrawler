package top.chende.shuinikg.crawl.cement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author: chende
 * @date: 2020/1/13 11:16
 * @description: 数字水泥网
 */
public class DcementCrawl {

    //搜索 "烧成"  总共101页  1004条记录
    static  String urltemplate="http://www.dcement.com/search/s.aspx?c=&f=0&wd=%u70e7%u6210&o=1&p=";

    static int tatalPage=50;

    public static void main(String[] args) {
        String u;
        int count=0; //
        for(int i=1;i<=tatalPage;i++){
            u=urltemplate+i;
            // System.out.println(u);
            try {
                Document document =  Jsoup.connect(u)
                        .timeout(10*1000)
                        .get();
                Element searchList=document.getElementsByClass("searchList").first();
                Elements  elements=  searchList.getElementsByTag("li");
                Iterator<Element> it=elements.iterator();

                while (it.hasNext()){
                    Element e=it.next();
                    String title=e.getElementsByTag("h2").first().text();
                    //段落
                    String p=e.getElementsByTag("p").first().text();
                    //链接
                    String url=e.getElementsByTag("em").first().text();
                    count++;
                    //  System.out.println(count+":  "+e.text());
                    System.out.println(title+"    "+p);
                   // System.out.println(p);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
