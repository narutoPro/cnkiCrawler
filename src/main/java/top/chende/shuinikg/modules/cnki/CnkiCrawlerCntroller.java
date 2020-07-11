package top.chende.shuinikg.modules.cnki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: chende
 * @date: 2020/7/10 05:11
 * @description: 知网爬虫
 */
@Controller
public class CnkiCrawlerCntroller {

    @Autowired
     CnkiCrawlerService cnkiCrawlerService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){

        return "hello cnki crawl";
    }

    /** 先简单的试试*/
    /**
     * https://kns.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&isinEn=1&dbPrefix=CFLS&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=on&t=1594448091470&keyValue=&S=1&sorttype=
     * https://kns.cnki.net/kns/brief/brief.aspx?curpage=3&RecordsPerPage=50&QueryID=13&ID=&turnpage=1&tpagemode=L&dbPrefix=CFLS&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx&isinEn=1&
     * @param start
     * @param url
     * @param clc
     * @param cookie
     * @param totalPages
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public String collectTest(
            int start,
            String url,
            String clc,
            String cookie,
            int totalPages

    ) {
        cnkiCrawlerService.collectDemo(start,url,clc,cookie,totalPages);

        return "collect";
    }



    /**
     * 参数太长！
     *  json文件保存
     * */
    @RequestMapping(value = "/crawlcnki", method = RequestMethod.POST)
    public String collectStart(
            int start,
            String url,
            String clc,
            String cookie,
            int totalPages

    ) {

        return "collect";
    }
}
