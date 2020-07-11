package top.chende.shuinikg.modules.cnki;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.model.ArticleDetail;
import top.chende.shuinikg.util.ObjPropertyPrintUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析页面
 */
public class PageParseUtil {

    private static Logger log	= LoggerFactory.getLogger(PageParseUtil.class);

    static String listClass = "GridTableContent";


    //TODO 解析文章详情 获取摘要 作者单位 关键词等信息

    String record = "id:null,title:生活垃圾灰渣冷却机设计开发及运行实践,author:陈士超; 朱雯; 王凯; 曾伟兵,resource:矿山机械,publishDate:2017-05-10,db:期刊,indexed:0,downloaded:30,downloadPath:../download.aspx?filename=thmekRHZ3Ujcph0VYpGUuRGT19SaCRXcoxkNK1GZ1NFZCdkcwUHW4BjMiFGOhhWWmdTauhUbWRlRvJmSSZkT1YTWWB1KRRnV6d1THdlNUZTW1AFMm1WblFGUtl1c6ZUeTVVSapUT1MESl9CbQNFU39yKJNDcpdlZ&tablename=CJFDLAST2017,detailPath:/kns/detail/detail.aspx?QueryID=8&CurRec=25&recid=&FileName=KSJX201705014&DbName=CJFDLAST2017&DbCode=CJFQ&yx=&pr=&URLID=&bsm=QK0201;,";
    //detailPath:/kns/detail/detail.aspx?QueryID=8&CurRec=25&recid=&FileName=KSJX201705014&DbName=CJFDLAST2017&DbCode=CJFQ&yx=&pr=&URLID=&bsm=QK0201;
    static String path = "/kns/detail/detail.aspx?QueryID=8&CurRec=25&recid=&FileName=KSJX201705014&DbName=CJFDLAST2017&DbCode=CJFQ&yx=&pr=&URLID=&bsm=QK0201;";
    static String detailDomain = "http://kns.cnki.net/KCMS";
    static int exCount=10;

    /**
     * 连接超时
     *
     * @param path 文章详情地址
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
            document = Jsoup.connect(url)
                   // .timeout(10*1000)
                    .get();
            //  System.out.println(document.text());
        } catch (IOException e) {
            e.printStackTrace();
           // System.err.println("getArticleDetailHtml Exception"+exCount);
            log.error("getArticleDetailHtml Exception重连次数{},url:{}",exCount,url);
            return null;
//            exCount--;
//            if (exCount>=0)
//                return getArticleDetailHtml(path);
//            else
//            {
//                return null;
//            }
        }
        return document;
    }

    public static ArticleDetail parserDoc2ArticleDetail(Document document) {
        //重新设置短线重连请求次数
        exCount=5;
        ArticleDetail articleDetail = new ArticleDetail();
        String html=document.html();

        // orgn
        if (document.html().contains("orgn")&& document.getElementsByClass("orgn")!=null){
            String orgn="";
            try {
                orgn = document.getElementsByClass("orgn").first().text();
            }catch (Exception e){
                e.printStackTrace();
                log.error("get orgn fail"+document.outerHtml());
            }


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




    public static List<ArticleBrief> parser(String html) {

        List<ArticleBrief> articleBriefs=new ArrayList<>();
        Document document = Jsoup.parse(html);
        //获取文章列表元素
        Element table = document.getElementsByClass(listClass).first();
        log.info(" table is {}",table.text());
        List<Element> elements = table.getElementsByTag("TR");
        for (int i = 1; i < elements.size   (); i++) {
            //System.out.println("#######解析第   " + i + "  行数据########");
            articleBriefs.add(parser2ArticleBrief(elements.get(i)));
            //System.out.println("#################################");
        }
        return articleBriefs;
    }


    /**
     * 解析列表标签<TR> 里的元素
     * <p>
     * html() 和 outerhtml() 的区别：第5个元素html ：期刊
     * 第5个元素outhtml ：<td align="center"> 期刊 </td>
     *
     * @param tr
     * @return
     */
    public static ArticleBrief parser2Articlebri(Element tr) {
        Elements tds = tr.getElementsByTag("td");
        for (int i = 1; i < tds.size(); i++) {
            Element td = tds.get(i);
            //     System.out.println("第" + i + "个元素html ：" + td.html());
            System.out.println("第" + i + "个元素outhtml ：" + td.outerHtml());

        }
        return null;
    }

    /**
     *
     * @param tr
     * @return
     */
    public static ArticleBrief parser2ArticleBrief(Element tr) {
        Elements tds = tr.getElementsByTag("td");
        ArticleBrief articleBrief=new ArticleBrief();

        // 1.标题
        Element titleE=tds.get(1);

        /** 文章详情地址 */
        String artiledetailPath=titleE.getElementsByTag("a").attr("href");

        String title=titleE.text();
        // System.out.println(title+artiledetailPath);

        articleBrief.setTitle(title);
        articleBrief.setDetailPath(artiledetailPath);
        // 2.作者
        String author=tds.get(2).text();
        // 作者长度太长
        if (author.length()>245)
            author=author.substring(0,245);
        articleBrief.setAuthor(author);



        //3.来源  江西建材
        articleBrief.setResource(tds.get(3).text());

        //4.发表时间
        articleBrief.setPublishDate(tds.get(4).text());

        //5.db
        articleBrief.setDb(tds.get(5).text());

        //6.被引次数  null或者""
        String indexedStr=tds.get(6).text();
        Integer index;
        if (indexedStr==null || indexedStr.equals(""))
            index=0;
        else
            index=new Integer(indexedStr);
        articleBrief.setIndexed(index);

        //7.下载次数
        String downloadStr=tds.get(7).text();
        Integer downloaded;
        if (downloadStr==null || downloadStr.equals(""))
            downloaded=0;
        else
            downloaded=new Integer(downloadStr);
        articleBrief.setDownloaded(downloaded);

        String downloadPath=tds.get(7).getElementsByTag("a").attr("href");
        //.attr("href");
        articleBrief.setDownloadPath(downloadPath);

   //     System.out.println(ObjPropertyPrintUtil.toString(articleBrief,ArticleBrief.class));
        return articleBrief;
    }
}
