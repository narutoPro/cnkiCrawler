package top.chende.shuinikg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.chende.shuinikg.service.TextClassificationService;

/**
 * @author: chende
 * @date: 2020/2/9 10:07
 * @description:
 */
@RestController
public class TextClassificationController {
@Autowired
TextClassificationService textClassificationService;

@RequestMapping("/printAbs")
    public String printAbstract(){

        return textClassificationService.printAbstract();
    }
}
