package top.chende.shuinikg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.model.SearchPage;
import top.chende.shuinikg.service.SearchPageService;

/**
 * @author: chende
 * @date: 2019/7/23 22:30
 * @description:
 */
@Controller
public class SearchPageController {

    @Autowired
    SearchPageService searchPageService;

    @RequestMapping(value = "/brief/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ArticleBrief briefDetail(@PathVariable("id") Long id) {

        return searchPageService.articleBriefDetail(id);
    }

    @RequestMapping(value = "/collectpages", method = RequestMethod.GET)
    public String index() {
        return "collect";
    }

    /**
     * 解析所有收集到的网页 结果保存到数据库
     * @return
     */
    @RequestMapping(value = "/parserall", method = RequestMethod.GET)
    @ResponseBody
    public String parserAll() {
        searchPageService.parserAll();
        //  System.out.println(re.getHtml());
        return "解析成功";
    }

    //解析存在数据库中的网页列表数据
    @RequestMapping(value = "/parser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String parser(@PathVariable Long id) {
        searchPageService.parser(id);
      //  System.out.println(re.getHtml());
        return "ccc";
    }

    //解析爬取网页 解析成一条条文献积累
    //todo
  //"&amp；"就是'&' 只是在HTML中的&用&amp;来表示  详情链接地址需要字符转换
    //爬取页的详情
    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id) {
        SearchPage re=searchPageService.detail(id);
        System.out.println(re.getHtml());
        return re.getHtml();
    }

    /*
    * 慢慢爬
    * 已经爬取的分类：
    *
    *  CLC=TQ172.62
    *  CLC=TQ172.4
    *  CLC=TQ172.61
    *
    * */

    @RequestMapping(value = "/collecting", method = RequestMethod.POST)
    public String collect(
            String url,
            String clc,
            String cookie,
            int totalPages

    ) {
        searchPageService.collect(url, clc, cookie, totalPages);
        return "collect";
    }

    /**
    * 参数太长！
    *
    * */
    @RequestMapping(value = "/collectingstart", method = RequestMethod.POST)
    public String collectStart(
            int start,
            String url,
            String clc,
            String cookie,
            int totalPages

    ) {
        searchPageService.collect(start, url, clc, cookie, totalPages);
        return "collect";
    }

}
