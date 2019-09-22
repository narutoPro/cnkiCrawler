package top.chende.shuinikg.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.chende.shuinikg.dto.IDs;

import java.util.List;

/**
 * @author: chende
 * @date: 2019/8/23 23:23
 * @description:
 */
@Controller
public class DemoController {
    @RequestMapping("/fetch")
    @ResponseBody
    public String test(
            @RequestBody String name){
        System.out.println("name:"+name);
    //    String json="{\"name\":[\"1\",\"2\"]}";
        String json=name;
        Gson gson=new Gson();
         IDs isd=gson.fromJson(json, IDs.class);
        List<String> id=isd.getIds();
        for (String string:id){
            System.out.println("id  :"+string);
        }
     //   System.out.println();
     //   String json="{\"name\":[\"1\",\"2\"]}";
      //  JSONPObject jsonpObject=new JSONPObject(json);

        return name;
//name的值为 {"name":["1","2"]}
    }
}
