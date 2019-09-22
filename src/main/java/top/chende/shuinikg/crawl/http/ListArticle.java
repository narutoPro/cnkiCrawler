package top.chende.shuinikg.crawl.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author: chende
 * @date: 2019/7/23 16:32
 * @description: 获取文献列表
 */

public class ListArticle {

    //中图分类号 CLC=TQ172.6
    String clc;

    String url;

    String cookie="ASP.NET_SessionId=szdxmanmd0adsiex4yakm0p4; Ecp_ClientId=3190723083400975069; SID_kns=123114; SID_klogin=125142; Ecp_session=1; KNS_SortType=; RsPerPage=20; SID_krsnew=125132; cnkiUserKey=d0a78587-3d4b-5b76-698a-95637c2f99c0; SID_kcms=124105; SID_kinfo=125104; LID=WEEvREdxOWJmbC9oM1NjYkZCbDdrdXJMc012cFBCalpLc0hqb0FIcDIzdUU=$R1yZ0H6jyaa0en3RxVUd8df-oHi7XMMDo7mtKT6mSmEvTuk11l2gFA!!; _pk_ses=*";


    //刷新cookie http://kns.cnki.net/kns/
    //总页数
    int tatalPages;
    //当前页  1 2 3 。。。。
    int curpage;



    public Document getDocument(int curpage) throws IOException {
        return  Jsoup.connect(url)
                .header("Cookie",cookie)
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .data("curpage",curpage+"")
                .get();
    }

    public String getClc() {
        return clc;
    }

    public void setClc(String clc) {
        this.clc = clc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getTatalPages() {
        return tatalPages;
    }

    public void setTatalPages(int tatalPages) {
        this.tatalPages = tatalPages;
    }

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }
}
