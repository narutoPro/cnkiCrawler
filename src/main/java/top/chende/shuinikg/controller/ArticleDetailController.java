package top.chende.shuinikg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.chende.shuinikg.model.ArticleDetail;
import top.chende.shuinikg.service.ArticleDetailService;

import java.util.List;
import java.util.Map;

/**
 * @author: chende
 * @date: 2019/7/25 17:03
 * @description:
 */
@Controller
public class ArticleDetailController {

    @Autowired
    ArticleDetailService articleDetailService;

    /**
     * 导出所有标题到文件
     * @return
     */
    @RequestMapping("/titles")
    @ResponseBody
    public List allTitles(){

        return articleDetailService.allTitles();

    }

    @RequestMapping("/kwyear")
    @ResponseBody
    public Map keywords(String year){

        return  articleDetailService.keywordsByCondition(year);
    }
    /**
     *
     * @return
     */
    @RequestMapping("/keywords")
    @ResponseBody
    public Map keywords(){

        return  articleDetailService.keywords();
    }

    @RequestMapping("/nullerr")
    @ResponseBody
    public List nullerr(){

        //  articleDetailService.exprotAbstract("test");
        return  articleDetailService.findAllByArticleDetailAbstractIsNull();
    }

    /**
     * 很多摘要是空值呀！！！  解析或者获取html时 异常
     * 有247个为空呀！
     * @return
     */
    @RequestMapping("/export")
    @ResponseBody
    public String exprotAbstract(){

      //  articleDetailService.exprotAbstract("test");
        articleDetailService.exp();
        return  "success";
    }

    @RequestMapping(value = "/abstrcts")
    @ResponseBody
    public List absList(int page,int size){

        return articleDetailService.absList();
    }


    @RequestMapping(value = "/allids")
    @ResponseBody
    public List allIds(){
        return articleDetailService.allids();
    }

    @RequestMapping(value = "/detail/all")
    @ResponseBody
    public String all(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("新建线程来执行");
                articleDetailService.getDetailAndParserSaveAll();
            }
        }).start();

        return "articleDetailService.getDetailAndParserSaveAll() !!!";
    }
    @RequestMapping(value = "/detail/test")
    @ResponseBody
    public String test(){
      articleDetailService.test();
      return "test success";
    }

    /**
     *
     * @param id articleBrief ID
     * @return
     */
    @RequestMapping(value = "/detailid/{id}")
    @ResponseBody
    public ArticleDetail parserById(@PathVariable("id") Long id){

        return articleDetailService.detailById(id);
    }

}
