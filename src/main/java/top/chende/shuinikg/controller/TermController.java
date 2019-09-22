package top.chende.shuinikg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.chende.shuinikg.model.Term;
import top.chende.shuinikg.service.TermService;

import java.util.List;

/**
 * @author: chende
 * @date: 2019/7/22 22:44
 * @description:
 */
@Controller
public class TermController {

    @Autowired
    TermService termService;

    @RequestMapping("/file")
    @ResponseBody
    public List fileTerm(){
        String PATH="/Users/chende/Desktop/term2.txt";
        return termService.getTermFile(PATH);

    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/term",method = RequestMethod.POST)
    @ResponseBody
    public Term addTerm(
     Term term
    ){
        return termService.addTerm(term) ;
    }

    @RequestMapping(value = "/term/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Term termDetail(
         @PathVariable("id") Long id
    ){
        //return termService.addTerm(term) ;
        return termService.termDetail(id);
    }
    @RequestMapping(value = "/term/all",method = RequestMethod.GET)
    @ResponseBody
    public List allTerm(){
        return termService.allTerm() ;
    }
}
