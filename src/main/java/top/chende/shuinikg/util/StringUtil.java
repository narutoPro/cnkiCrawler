package top.chende.shuinikg.util;

/**
 * @author: chende
 * @date: 2020/2/9 10:41
 * @description:
 */
public class StringUtil {

    public static void main(String[] args) {
        String str="<正>项目简介本项目主要是对原有的污泥处置工艺线进行改造,污泥泵系统处置污泥能够与水泥窑有机的结合起来,从而不仅能够保证污泥泵系统的连续运转,同时减少了处置污泥对窑工况的影响。主要工艺路线如下:将废物和工业污泥分别放在储坑中进行存储,通过抓斗将物料送到搅拌器进行调质搅拌,搅拌均匀后的物料经管道输送到滚筛中进行筛选,滚筛将粒径小的";
        String[] ss=str.split("<正>");
        System.out.println(ss[1]);
    }
}
