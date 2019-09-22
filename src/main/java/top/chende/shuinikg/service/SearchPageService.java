package top.chende.shuinikg.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import top.chende.shuinikg.crawl.ParsePageList;
import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.model.SeachRecord;
import top.chende.shuinikg.model.SearchPage;
import top.chende.shuinikg.repository.ArticleBriefRepository;
import top.chende.shuinikg.repository.SeachRecordRepository;
import top.chende.shuinikg.repository.SearchPageRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chende
 * @date: 2019/7/23 22:40
 * @description:
 */
@Service
public class SearchPageService {
    @Autowired
    SearchPageRepository searchPageRepository;
    @Autowired
    SeachRecordRepository seachRecordRepository;
    @Autowired
    ArticleBriefRepository articleBriefRepository;
    @Autowired
    PlatformTransactionManager dataSourceTransactionManager;

    @Autowired
    ArticleDetailService articleDetailService;
    // DataSourceTransactionManager

    public ArticleBrief articleBriefDetail(Long id){
        return articleBriefRepository.findById(id).get();
    }

    /**
     * 将第一步爬取的searchPage  解析成articleBrief列表
     *
     * jpa默认建表时用的引擎为MyISAM，而MyISAM却是不支持事务的，要想支持需要将表的存储引擎设为InnoDB
     */
    @Transactional
    public void parserAll(){
        DefaultTransactionDefinition definition=new DefaultTransactionDefinition();
        definition.setName("插入解析的page");
        TransactionStatus status=dataSourceTransactionManager.getTransaction(definition);
        //
        List<ArticleBrief> articleBriefALL=new ArrayList<>();
        try {
            //解析没有被解析的页面
            List<SearchPage> pages=searchPageRepository.findAllByIsParsedIsNull();
            for (SearchPage page:pages){
                System.out.println("page id:"+page.getId()+"  isParsed:"+page.getIsParsed());
                List<ArticleBrief> articleBriefs=new ArrayList<>(ParsePageList.parser(page.getHtml()));
                articleBriefALL.addAll(articleBriefs);
                articleBriefRepository.saveAll(articleBriefs);
                page.setIsParsed(true);

            }
            searchPageRepository.saveAll(pages);
            //同时将articleBrief 获取详情articleDetail 并解析
            articleDetailService.getDetailAndParserSaveAll(articleBriefALL);
            articleBriefRepository.flush();
            searchPageRepository.flush();
        }catch (Exception ex){
            System.err.println(" parserAll() 出错 rollback");
            dataSourceTransactionManager.rollback(status);
        }

    }

    /**
     * 解析制定id的page 解析结果打印在控制台
     * 不保存在数据库
     * @param id
     */
    public void parser(Long id){
        SearchPage page=searchPageRepository.findById(id).get();

        ParsePageList.parser(page.getHtml());
       // page.setIsParsed(true);
       // searchPageRepository.save(page);


    }
    public SearchPage detail(Long id) {
        return searchPageRepository.findById(id).get();
    }

    public void collect(
            int start,
            String url,
            String clc,
            String cookie,
            int totalPages
    ) {
        //保存搜索记录
        SeachRecord seachRecord = new SeachRecord(url, clc, cookie, new Long(totalPages));
        seachRecordRepository.save(seachRecord);
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
            searchPageRepository.save(searchPage);
            System.out.println(clc + " save page" + curpage);
        }
        searchPageRepository.flush();
    }


    public void collect(
            String url,
            String clc,
            String cookie,
            int totalPages
    )  {
        //保存搜索记录
        SeachRecord seachRecord = new SeachRecord(url, clc, cookie, new Long(totalPages));
        seachRecordRepository.save(seachRecord);

        for (int curpage = 1; curpage <= totalPages; curpage++) {
            Document document = null;
            try {
                document = Jsoup.connect(url)
                        .header("Cookie", cookie)
                        .data("curpage", curpage + "")
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (document.text().contains("请输入验证码")) {
                System.err.println("验证码大哥来了" + curpage);
                return;
            }
            SearchPage searchPage = new SearchPage();
            searchPage.setClc(clc);
            searchPage.setCurpage(curpage);
            searchPage.setHtml(document.outerHtml());
            searchPageRepository.save(searchPage);
            System.out.println(clc + " save page" + curpage);

        }
        searchPageRepository.flush();
    }
}
