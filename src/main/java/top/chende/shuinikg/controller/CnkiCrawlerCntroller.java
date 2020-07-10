package top.chende.shuinikg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.chende.shuinikg.service.CnkiCrawlerService;

/**
 * @author: chende
 * @date: 2020/7/10 05:11
 * @description: 知网爬虫
 */
@Controller
public class CnkiCrawlerCntroller {

    @Autowired
     CnkiCrawlerService cnkiCrawlerService;
    /**
     * 参数太长！
     *  json文件保存
     * */
    @RequestMapping(value = "/collectingstart", method = RequestMethod.POST)
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
