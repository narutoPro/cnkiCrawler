package top.chende.shuinikg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.chende.shuinikg.model.CementPage;
import top.chende.shuinikg.service.OtherPlatformCrawlService;

/**
 * @author: chende
 * @date: 2019/11/18 10:16
 * @description:  爬取其他平台的网页
 *
 * 手动输入网页
 *
 */
@Controller
public class OtherPlatformCrawlController {

    @Autowired
    private  OtherPlatformCrawlService otherPlatformCrawlService;

    @RequestMapping("/startf")
    @ResponseBody
    public String startFirst(){
        otherPlatformCrawlService.firstStartCrawl();
        return "startFirst";
    }

    @RequestMapping("/stop")
    @ResponseBody
    public String stop(){
        otherPlatformCrawlService.stop();
        return "stop";
    }

    @RequestMapping("/restart")
    @ResponseBody
    public String restart(){
        otherPlatformCrawlService.restart();
        return "restart";
    }

    @RequestMapping("/shutdown")
    @ResponseBody
    public String shutdown(){
        otherPlatformCrawlService.shutdown();
        return "shutdown";
    }

    @RequestMapping("/cementdetail/{id}")
    @ResponseBody
    public CementPage cementdetail(@PathVariable("id") Long id){

        return otherPlatformCrawlService.cementdetail(id);
    }

}
