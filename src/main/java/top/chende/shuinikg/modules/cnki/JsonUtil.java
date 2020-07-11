package top.chende.shuinikg.modules.cnki;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonUtil {

    public static final String JSON_FILE="./cnki_data.json";
    //todo 先从文件读取数组  写入数据  在写入到文件
    public static void writeCnkiData(CnkiEntity cnkiEntity){
        List<CnkiEntity> list=new ArrayList<>();
        list.add(cnkiEntity);
        writeCnkiData(list);
    }

    /**
     * 批量写入数据
     * @param cnkiEntity
     */
    public static void writeCnkiData(List<CnkiEntity> cnkiEntity){
        try {
            FileReader fileReader=new FileReader(JSON_FILE);
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            //将JSON的String 转成一个JsonArray对象
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();

            Gson gson = new Gson();
            ArrayList<CnkiEntity> cnkiEntities = new ArrayList<>();
            //加强for循环遍历JsonArray
            for (JsonElement element : jsonArray) {
                //使用GSON，直接转成Bean对象
                CnkiEntity e = gson.fromJson(element, CnkiEntity.class);
                cnkiEntities.add(e);
            }
            cnkiEntities.addAll(cnkiEntity);

            //在写入文件
            FileWriter fw = new FileWriter(JSON_FILE);
            PrintWriter out = new PrintWriter(fw);
            out.write(gson.toJson(cnkiEntities));
            out.println();
            fw.close();
            out.close();
            fileReader.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e){

        }
    }




    public static CnkiEntity getCnkiEntity(){
        CnkiEntity cnkiEntity=new CnkiEntity();
        cnkiEntity.setArticleAbstract("abstarascac");
        cnkiEntity.setAuthor("chende");
        cnkiEntity.setCollectDate(new Date().toString());
        return cnkiEntity;
    }

    /**
     * 解析没有数据头的纯数组
     */
    public static void parseNoHeaderJArray(String strByJson) {

        //拿到本地JSON 并转成String
//        String strByJson = JsonToStringUtil.getStringByJson(this, R.raw.juser_1);

        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

        Gson gson = new Gson();
        ArrayList<CnkiEntity> userBeanList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            CnkiEntity cnkiEntity = gson.fromJson(user, CnkiEntity.class);
            userBeanList.add(cnkiEntity);
        }

    }

    public static void main(String[] args) {
        Gson gson=new Gson();

        List<CnkiEntity > list=new ArrayList<>();

        for (int i=0;i<10;i++){
            list.add(getCnkiEntity());
        }

        try {
            writeFile(gson.toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(gson.toJson(list));

        String listCnkiStr=gson.toJson(list);

        List ll=gson.fromJson(listCnkiStr,List.class);
        for (Object o
        :ll){
            System.out.println(o);
//            CnkiEntity entity=gson.fromJson(o.toString(),CnkiEntity.class);
//            System.out.println(entity.getAuthor());
        }

    }


    public static void writeFile( String sets)
            throws IOException {
        String filePath="./test.json";
        FileWriter fw = new FileWriter(filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(sets);
        out.println();
        fw.close();
        out.close();
    }


    public static void write( Object object,String url){
        Gson gson=new Gson();
        JsonObject jsonObject=new JsonObject();
        List<String > list=new ArrayList<>();
        list.add("lsit1");
        list.add("lsit2");
        jsonObject.addProperty("key",gson.toJson(list));
        System.out.println(gson.toJson(list));
    }

}
