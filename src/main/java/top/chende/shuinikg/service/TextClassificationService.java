package top.chende.shuinikg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.chende.shuinikg.model.ArticleDetail;
import top.chende.shuinikg.repository.ArticleDetailRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: chende
 * @date: 2020/2/9 10:08
 * @description:
 */
@Service
public class TextClassificationService {

    @Autowired
    ArticleDetailRepository articleDetailRepository;

    String  datapath="/Users/chende/Desktop/new-dataset";

    public String printAbstract(){
        List<ArticleDetail>  articleDetails=articleDetailRepository.findAll();

        StringBuilder sb=new StringBuilder();
        List<String>  data=processData(articleDetails);

        System.out.println("data size:"+data.size());//data size:3082
        for (String s:data){
            System.out.print("水泥"+'\t'+s+'\n');
            sb.append("水泥"+'\t'+s+'\n');
        }
        File f=new File(datapath,"cement.txt");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(f,true));
            out.write(sb.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  sb.toString();

    }


    public List processData(List<ArticleDetail>  articleDetails){
        List<String>  data=new ArrayList<>();
        for (ArticleDetail articleDetail :articleDetails){
            String abs=articleDetail.getArticleAbstract();
            if (abs==null || abs.equals("")){continue;}
            else if (abs.startsWith("<正>")){
                String[] ss=abs.split("<正>");
                data.add(ss[1].trim());
            }
            else if (!isContainChinese(abs)){continue;}
            else {
                data.add(abs.trim());
            }
        }
        return data;

    }

    /**
     * 需要对数据进行处理：
     * 1.null
     * 2.<正>
     * 3.英文
     * <正>项目简介本项目主要是对原有的污泥处置工艺线进行改造,污泥泵系统处置污泥能够与水泥窑有机的结合起来,从而不仅能够保证污泥泵系统的连续运转,同时减少了处置污泥对窑工况的影响。主要工艺路线如下:将废物和工业污泥分别放在储坑中进行存储,通过抓斗将物料送到搅拌器进行调质搅拌,搅拌均匀后的物料经管道输送到滚筛中进行筛选,滚筛将粒径小的
       Researching and developing chrome-free refractory becomes a research hotspot at present because hexavalent chromium ions(Cr6+) may lead to environmental hazard. The additives lanthanum oxide(La2O3), e

     */
    public void processData(String abs){
        if (abs==null || abs.equals(""))
        { System.out.println("空值");
        return;
        }
        if (abs.startsWith("<正>")){
            System.out.println(abs);
        }
        //判断纯英文
        if(!isContainChinese(abs)){
            System.out.println(abs);
        }

    }


    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public  boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
