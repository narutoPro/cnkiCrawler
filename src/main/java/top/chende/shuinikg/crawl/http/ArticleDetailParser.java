package top.chende.shuinikg.crawl.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import top.chende.shuinikg.model.ArticleDetail;

import java.io.IOException;

/**
 * @author: chende
 * @date: 2019/7/25 15:55
 * @description:
 */
public class ArticleDetailParser {

    String record = "id:null,title:生活垃圾灰渣冷却机设计开发及运行实践,author:陈士超; 朱雯; 王凯; 曾伟兵,resource:矿山机械,publishDate:2017-05-10,db:期刊,indexed:0,downloaded:30,downloadPath:../download.aspx?filename=thmekRHZ3Ujcph0VYpGUuRGT19SaCRXcoxkNK1GZ1NFZCdkcwUHW4BjMiFGOhhWWmdTauhUbWRlRvJmSSZkT1YTWWB1KRRnV6d1THdlNUZTW1AFMm1WblFGUtl1c6ZUeTVVSapUT1MESl9CbQNFU39yKJNDcpdlZ&tablename=CJFDLAST2017,detailPath:/kns/detail/detail.aspx?QueryID=8&CurRec=25&recid=&FileName=KSJX201705014&DbName=CJFDLAST2017&DbCode=CJFQ&yx=&pr=&URLID=&bsm=QK0201;,";
    //detailPath:/kns/detail/detail.aspx?QueryID=8&CurRec=25&recid=&FileName=KSJX201705014&DbName=CJFDLAST2017&DbCode=CJFQ&yx=&pr=&URLID=&bsm=QK0201;
    static String path = "/kns/detail/detail.aspx?QueryID=8&CurRec=25&recid=&FileName=KSJX201705014&DbName=CJFDLAST2017&DbCode=CJFQ&yx=&pr=&URLID=&bsm=QK0201;";
    static String detailDomain = "http://kns.cnki.net/KCMS";
     static int exCount=5;

    /**
     * 连接超时
     *
     * @param path
     * @return
     */
    public static Document getArticleDetailHtml(String path) {

        String url = detailDomain + path.split("/kns")[1];
        //去除空格
        url=url.replaceAll(" ","");
        //System.out.println("url:"+url);
        Document document = null;
        try {
        //    System.out.println("url："+url);
            document = Jsoup.connect(url).get();
          //  System.out.println(document.text());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("getArticleDetailHtml Exception"+exCount);
            exCount--;
            if (exCount>=0)
            return getArticleDetailHtml(path);
        }
        return document;
    }

    public static ArticleDetail parserDoc2ArticleDetail(Document document) {
        //重新设置短线重连请求次数
        exCount=5;
        ArticleDetail articleDetail = new ArticleDetail();
        String html=document.html();

        // orgn
        if (document.html().contains("orgn")){
        String orgn = document.getElementsByClass("orgn").first().text();
        if(orgn.length()>245)
            orgn=orgn.substring(0,245);
        articleDetail.setOrgn(orgn);
        }

        String keyWords = containID(document, "catalog_KEYWORD");
        articleDetail.setKeyWords(keyWords);
        if (document.html().contains("ChDivSummary")) {
            String abstr = document.getElementById("ChDivSummary").text();
            articleDetail.setArticleAbstract(abstr);
        }

        String doi = containID(document, "catalog_ZCDOI");
        articleDetail.setDoi(doi);

        String clc = containID(document, "catalog_ZTCLS");
        articleDetail.setClc(clc);

        String tutor = containID(document, "catalog_TUTOR");
        articleDetail.setTutor(tutor);

        String fund = containID(document, "catalog_FUND");
        /**
         * Data truncation: Data too long for column 'fund' at row 1
         */
        if (fund.length()>245){
            fund=fund.substring(0,245);
        }
        articleDetail.setFund(fund);

        return articleDetail;
    }

    public static String containID(Document document, String idStr) {
        if (document.html().contains(idStr)) {
           // System.out.println(document.html());
            //System.out.println(idStr);
            //获取元素时候 会产生异常
            try {
                Element parent=document.getElementById(idStr).parent();
                if (parent!=null)
                    return document.getElementById(idStr).parent().text();
            }catch (Exception ex){
                ex.printStackTrace();
                return "";
            }

        }

        return "";
    }


}
