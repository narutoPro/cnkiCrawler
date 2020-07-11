package top.chende.shuinikg.modules.cnki;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.model.ArticleDetail;
import top.chende.shuinikg.model.SearchPage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chende
 * @date: 2020/7/10 05:16
 * @description:
 */

@Service
public class CnkiCrawlerService {

    private static Logger log	= LoggerFactory.getLogger(CnkiCrawlerService.class);

    static String detailDomain = "http://kns.cnki.net/KCMS";

    int totalPages=0;

    public void collectDemo(int start,
                            String url,
                            String clc,
                            String cookie,
                            int totalPages) {
        List<ArticleBrief> articleBriefs=new ArrayList<>();
        int netErrorCount =0;

        for (int curpage = start; curpage <= totalPages; curpage++) {
            Document document = null;
            try {
                document = Jsoup.connect(url)
                        .header("Cookie", cookie)
                        .header("Connection", "keep-alive")
                        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                       // .timeout(10*1000)
                        .data("curpage", curpage + "")
                        .get();
                /** 判断验证码*/
                if (document.text().contains("请输入验证码")) {
                    log.error("验证码大哥来了,{}第{}页，cookie是{}", url, curpage,cookie);
                    //保存数据
                    List<CnkiDTO> cnkiDTOS=getDetailPageAndParse(articleBriefs);
                    writeCnkiDataJsonFile(cnkiDTOS,clc+"_"+totalPages+"cnki_data.json");
                    writeCnkiJsonDataLines(cnkiDTOS,clc+"_"+totalPages+"cnki_data.json");
                    return;
                }
                List<ArticleBrief>  list=PageParseUtil.parser(document.html());
                articleBriefs.addAll(list);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("网络出错第" + netErrorCount + "次出错 curpage：" + curpage);
               // System.err.println("网络出错第" + netErrorCount + "次出错 curpage：" + curpage);

            }
        }
        /**  获取详情页面  */
        List<CnkiDTO> cnkiDTOS=getDetailPageAndParse(articleBriefs);
        writeCnkiDataJsonFile(cnkiDTOS,clc+"_"+totalPages+"cnki_data.json");
        writeCnkiJsonDataLines(cnkiDTOS,clc+"_"+totalPages+"cnki_data.json");

        //在写入文件
        /**
         * 1.Gson对“=”一类的转码出现乱码
         * 解决方案：
         * Gson gs = new GsonBuilder().disableHtmlEscaping().create();
         * //.disableHtmlEscaping()能防止对“=”转换出现乱码
         */
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        try {
            FileWriter fw = new FileWriter("./brief.json");
            PrintWriter out = new PrintWriter(fw);
            out.write(gson.toJson(articleBriefs));
           // out.println();
            fw.close();
            out.close();
        }catch (Exception e){
            log.error(e.toString());
        }
        log.info("url：{}此次搜集到数据"+articleBriefs.size()+"条",url);
    }

    //解析详情页面
    public List<CnkiDTO> getDetailPageAndParse(List<ArticleBrief> articleBriefs ){
        /**  获取详情页面  */
        List<CnkiDTO> cnkiDTOS=new ArrayList<>();
        for (ArticleBrief articleBrief:articleBriefs
        ) {
            Document document = PageParseUtil.getArticleDetailHtml(articleBrief.getDetailPath());
            if (document==null) {
                log.info("获取详情超时");
                continue;
            }
            ArticleDetail articleDetail =PageParseUtil.parserDoc2ArticleDetail(document);
            CnkiDTO cnkiDTO=new CnkiDTO();
            cnkiDTO.setArticleBrief(articleBrief);
            cnkiDTO.setArticleDetail(articleDetail);
            cnkiDTOS.add(cnkiDTO);
            log.info("finish {} paper:{}",totalPages++ ,cnkiDTO.getArticleBrief().getTitle());
        }
        return cnkiDTOS;
    }



    /**
     * 将每一个数据记录一行json串 写入文件
     * 在文件尾追加
     * @param cnkiDTOS
     * @param fileName
     */
    public static void writeCnkiJsonDataLines(List<CnkiDTO> cnkiDTOS ,String fileName){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String filePath="./cnki_lines_data/"+fileName;
        try {
            FileWriter fw = new FileWriter(filePath,true);
            PrintWriter out = new PrintWriter(fw);
            for (CnkiDTO cnki:cnkiDTOS
                 ) {
                out.write(gson.toJson(cnki));
                out.println();
            }
            fw.close();
            out.close();
        }catch (Exception e){
            log.error(e.toString());
        }
        log.info("write json file {}",fileName);
    }


    public static void writeCnkiDataJsonFile(Object object,String fileName){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String filePath="./cnki_data/"+fileName;
        try {
            FileWriter fw = new FileWriter(filePath);
            PrintWriter out = new PrintWriter(fw);
            out.write(gson.toJson(object));
            fw.close();
            out.close();
        }catch (Exception e){
            log.error(e.toString());
        }
        log.info("write json file {}",fileName);
    }



    public void collect(
            int start,
            String url,
            String clc,
            String cookie,
            int totalPages
    ) {
        //保存搜索记录

        int netErrorCount = 0;

        for (int curpage = start; curpage <= totalPages; curpage++) {
            Document document = null;
            try {
                document = Jsoup.connect(url)
                        .header("Cookie", cookie)
                        .header("Connection", "keep-alive")
                        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                        .data("curpage", curpage + "")
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("网络出错第" + netErrorCount + "次出错 curpage：" + curpage);
                return;
            }
            SearchPage searchPage = new SearchPage();
            searchPage.setClc(clc);
            searchPage.setCurpage(curpage);
            searchPage.setHtml(document.outerHtml());
            if (document.text().contains("请输入验证码")) {
                System.err.println("验证码大哥来了" + curpage);
                return;
            }

            System.out.println(clc + " save page" + curpage);
        }
    }
}
