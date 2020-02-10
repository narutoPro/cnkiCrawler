package top.chende.shuinikg.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: chende
 * @date: 2020/2/9 11:27
 * @description:
 *
 * cement.txt 3039条数据
 *
训练集: 5000*10
验证集: 500*10
测试集: 1000*10

 除以2
 */
public class ProcessData {

    static String  datapath="/Users/chende/Desktop/new-dataset";
    static String[] categories ={"体育","财经","房产","家居","教育","科技","时尚","时政","游戏","娱乐"};
   // ['体育', '财经', '房产', '家居', '教育', '科技', '时尚', '时政', '游戏', '娱乐']


    public static void main(String[] args) {
         //500 2500 250
        int fixSize=250;
        String  newdatapath="/Users/chende/Desktop/new-dataset/data";
        File test=new File(datapath,"cnews.val.txt");

        File newdata=new File(newdatapath,"cnews.val.txt");


        StringBuilder dataStringBuffer=new StringBuilder();

        Map<String,Integer>  cateCount=new HashMap<>();
        int count=0;

        try {
            if (!newdata.exists()){
                newdata.createNewFile();
            }


            FileReader fileReader = new FileReader(test);
            BufferedReader fr=new BufferedReader(fileReader);

            BufferedWriter fw=new BufferedWriter(new FileWriter(newdata));
            while(fr.ready()){
                String s=fr.readLine();
                String[] ss=s.split("\t");
                String key=ss[0];
                if (!cateCount.containsKey(key)){
                    cateCount.put(key,1);
                    dataStringBuffer.append(s+"\n");

                }
                else {
                    int num=cateCount.get(key);
                    if (num<fixSize){
                        cateCount.put(key,num+1);
                        dataStringBuffer.append(s+"\n");
                    }
                }

                count++;
            }
            //写入文件
            fw.write(dataStringBuffer.toString());
            fw.flush();
            System.out.println(dataStringBuffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("count:"+count);
    }
}
