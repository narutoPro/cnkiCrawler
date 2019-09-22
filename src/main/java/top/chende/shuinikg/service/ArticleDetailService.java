package top.chende.shuinikg.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import top.chende.shuinikg.crawl.http.ArticleDetailParser;
import top.chende.shuinikg.dto.ArticleSumDTO;
import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.model.ArticleDetail;
import top.chende.shuinikg.repository.ArticleBriefRepository;
import top.chende.shuinikg.repository.ArticleDetailRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author: chende
 * @date: 2019/7/25 17:05
 * @description:
 */
@Service
public class ArticleDetailService {
    @Autowired
    ArticleDetailRepository articleDetailRepository;
    @Autowired
    ArticleBriefRepository articleBriefRepository;
    @Autowired
    PlatformTransactionManager dataSourceTransactionManager;

    String path = "/Users/chende/Desktop/title.txt";

    /**
     * @param title
     * @return
     */
    public boolean isEnglishTitle(String title) {

        for (int i = 0; i < title.length(); i++) {
            char ch = title.charAt(i);
            if (ch == ' ' || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) {

            } else {
                return false;
            }

        }
        System.out.println(title + " :is english title!");
        return true;
    }

    /**
     * 所有标题 写入到文件
     *
     * @return
     */
    public List allTitles() {
        List<ArticleBrief> list = articleBriefRepository.findAll();
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(path, true));
            for (int i = 0; i < list.size(); i++) {
                ArticleBrief sum = list.get(i);
                if (sum.getTitle() == null || sum.getTitle().equals("")) continue;
                //需要去除英文标题
                if (!isEnglishTitle(sum.getTitle())) {
                    bfw.write(sum.getTitle());
                    bfw.newLine();
                }
            }
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Map keywordsByCondition(String clc, String year) {


        return null;
    }

    public Map keywordsByCondition(String year) {


        HashMap<String, Integer> keys = new HashMap<>();
        //计算词出现次数 * 下载数
        HashMap<String, Integer> hot = new HashMap<>();
        //查找所有
        year = year + "-%";
        List<ArticleDetail> details = articleDetailRepository.findByPublishYear(year);
        //查找制定clc目录的统计
        //List<ArticleDetail> details = articleDetailRepository.findAllByClcContaining("TQ172.62");
        System.out.println("统计" + year + " 年的数据，总共" + details.size() + "条");
        for (ArticleDetail detail : details
                ) {
            Long briefId = detail.getActicleBriefId();
            ArticleBrief brief = articleBriefRepository.findById(briefId).get();
            int beDownloaded = 0;
            Integer down = brief.getDownloaded();
            if (down != null && down.intValue() != 0) {
                beDownloaded = down.intValue();
            }

            String skey = detail.getKeyWords();
            if (skey != null && !skey.equals("")) {
                String[] kk = processKeywords(skey);
                for (String key : kk
                        ) {
                    key = key.trim();
                    if (keys.containsKey(key)) {
                        hot.put(key, hot.get(key) + beDownloaded);
                        keys.put(key, keys.get(key) + 1);
                    } else {

                        hot.put(key, beDownloaded);
                        keys.put(key, 1);
                    }
                }
            }
        }
        //    System.out.println("keys size:"+keys.size());
        sortMap(keys);
        System.out.println("############分割线#########");
        sortMap(hot);


        return keys;
    }


    /**
     * 关键词：低导热多层复合莫来石砖; 隔热; 保温; 节能; 预分解窑;
     */
    public Map keywords() {
        HashMap<String, Integer> keys = new HashMap<>();
        //计算词出现次数 * 下载数
        HashMap<String, Integer> hot = new HashMap<>();
        //查找所有
        List<ArticleDetail> details = articleDetailRepository.findAll();
        //查找制定clc目录的统计
        //List<ArticleDetail> details = articleDetailRepository.findAllByClcContaining("TQ172.62");
        System.out.println("TQ172.62 烧成系统统计" + details.size());
        for (ArticleDetail detail : details
                ) {
            Long briefId = detail.getActicleBriefId();
            ArticleBrief brief = articleBriefRepository.findById(briefId).get();
            int beDownloaded = 0;
            Integer down = brief.getDownloaded();
            if (down != null && down.intValue() != 0) {
                beDownloaded = down.intValue();
            }

            String skey = detail.getKeyWords();
            if (skey != null && !skey.equals("")) {
                String[] kk = processKeywords(skey);
                for (String key : kk
                        ) {
                    key = key.trim();
                    if (keys.containsKey(key)) {
                        hot.put(key, hot.get(key) + beDownloaded);
                        keys.put(key, keys.get(key) + 1);
                    } else {

                        hot.put(key, beDownloaded);
                        keys.put(key, 1);
                    }
                }
            }
        }
        //    System.out.println("keys size:"+keys.size());
        sortMap(keys);
        System.out.println("############分割线#########");
        sortMap(hot);


        return keys;
    }

    public void sortMap(Map<String, Integer> keys) {
        //排序一下
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(keys.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //升序排序
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }

        });

        for (Map.Entry<String, Integer> mapping : list) {
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }

    }

    public String[] processKeywords(String keys) {
        // String keys="关键词：低导热多层复合莫来石砖; 隔热; 保温; 节能; 预分解窑;";
        keys = keys.replaceAll("关键词：", "");
        String[] kk = keys.split(";");
        return kk;

    }


    /**
     * 在对错误url处理
     * 在重新获取解析保存article detail
     * 删除 is null的记录
     */
    public void nullAbstractError() {
        List<ArticleBrief> articleBriefs = articleBriefRepository.findAllByArticleDetailAbstractIsNull();

    }

    public List findAllByArticleDetailAbstractIsNull() {
        return articleBriefRepository.findAllByArticleDetailAbstractIsNull();
    }


    /**
     * bug原因在 url路径名不对
     * 如下面的url  DbCode= CJFD 多一个空格
     * 找了两个 null错误的，都是这个原因
     * detail/detail.aspx?QueryID=0&CurRec=88&DbCode= CJFD&dbname=CJFDLAST2019&filename=YHYJ201804013&urlid=&yx=
     */

    public String removeBlank(String url) {
        return url.replaceAll(" ", "");
    }

    public void exp() {
        List<ArticleDetail> list = articleDetailRepository.findAll();
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(path, true));
            for (int i = 0; i < list.size(); i++) {
                ArticleDetail sum = list.get(i);
                if (sum.getArticleAbstract() == null) continue;
                //  bfw.write(i+"######"+sum.getId()+"######"+sum.getArticleAbstract());
                bfw.write(sum.getArticleAbstract());
                bfw.newLine();
            }
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exprotAbstract(String savePath) {
        List<ArticleSumDTO> sums = articleDetailRepository.findDto();

        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(path, true));
            for (int i = 0; i < sums.size(); i++) {
                ArticleSumDTO sum = sums.get(i);
                bfw.write(i + "######" + sum.getId() + "######" + sum.getSum());
                bfw.newLine();
            }
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List absList() {
        return articleDetailRepository.findDto();
    }

    public List allids() {
        List<ArticleDetail> ids =
                articleDetailRepository.findAllBriefId();
        System.out.println("ids size:" + ids.size());
        return ids;
    }

    @Transactional
    public void getDetailAndParserSaveAll(List<ArticleBrief> dtos) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("爬取详情页 解析并插入数据库");
        TransactionStatus status = dataSourceTransactionManager.getTransaction(definition);
        List<ArticleDetail> articleDetails = new ArrayList<>();
        int count = dtos.size();
        for (ArticleBrief dto : dtos) {
            ArticleDetail articleDetail;
            if (dto.getDetailPath() != null)
                //todo 解析时容易产生异常

                articleDetail = detailById(dto);
            else {
                System.err.println("getDetailPath is null");
                continue;
            }
            System.err.println("brief ID:" + dto.getId() + "剩余" + count + "个记录");
            count--;
            if (articleDetail != null)
                articleDetails.add(articleDetail);
        }
        try {
            articleDetailRepository.saveAll(articleDetails);
            articleDetailRepository.flush();
/**
 * 发生异常无法定位异常！！！
 */
        } catch (Exception ex) {
            System.err.println(" getDetailAndParserSaveAll出错 rollback!!!");
            dataSourceTransactionManager.rollback(status);
        }
    }


    /**
     * 错误记录
     * brief ID:6487剩余1702个记录

     */
    /**
     * 爬取和 别的过程应该分开；
     */
    @Transactional
    public void getDetailAndParserSaveAll() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("爬取详情页 解析并插入数据库");
        TransactionStatus status = dataSourceTransactionManager.getTransaction(definition);

        List<ArticleBrief> dtos = articleBriefRepository.findAll();
        List<ArticleDetail> articleDetails = new ArrayList<>();
        int count = dtos.size();
        for (ArticleBrief dto : dtos) {
            ArticleDetail articleDetail;
            if (dto.getDetailPath() != null) {
                articleDetail = detailById(dto);
            }
            else {
                System.err.println("getDetailPath is null");
                continue;
            }
            //先测试一下 ，不保存到数据库
            System.err.println("brief ID:" + dto.getId() + "剩余" + count + "个记录");
            count--;
            //   System.out.println(ObjPropertyPrintUtil.toString(articleDetail, ArticleDetail.class));
            //   articleDetailRepository.save(articleDetail);
            if (articleDetail != null)
                articleDetails.add(articleDetail);
        }
        try {
            articleDetailRepository.saveAll(articleDetails);
            articleDetailRepository.flush();
/**
 * 发生异常无法定位异常！！！
 * 原因是try 中不要写太多其他代码，异常要对应
 */
        } catch (Exception ex) {
            System.err.println(" getDetailAndParserSaveAll出错 rollback!!!");
            dataSourceTransactionManager.rollback(status);
        }
    }

    public ArticleDetail detailById(ArticleBrief dto) {
        //   System.out.println("执行！！！ ---> id" + dto.getId());
        Document document;
        ArticleDetail articleDetail;
        if (dto.getDetailPath() != null) {
            document = ArticleDetailParser.getArticleDetailHtml(dto.getDetailPath());
            articleDetail = ArticleDetailParser.parserDoc2ArticleDetail(document);
            articleDetail.setActicleBriefId(dto.getId());
            return articleDetail;
        } else
            return null;

    }

    /**
     * 通过ArticleBrief的id 查询对应的ArticleDetail
     *
     * @param id
     * @return
     */
    public ArticleDetail detailById(Long id) {

        ArticleBrief articleBrief = articleBriefRepository.findById(id).get();
        Document document = ArticleDetailParser.getArticleDetailHtml(articleBrief.getDetailPath());
        //打印获取到到html
        //   System.out.println(document.html());
        ArticleDetail articleDetail = ArticleDetailParser.parserDoc2ArticleDetail(document);
        //打印解析结果
        //System.out.println(ObjPropertyPrintUtil.toString(articleDetail,ArticleDetail.class));
        articleDetail.setActicleBriefId(id);
        return articleDetail;
    }

    public void test() {

        List<ArticleBrief> articleBriefList = articleBriefRepository.findAll(new PageRequest(0, 100)).getContent();
        Document document;
        for (ArticleBrief articleBrief : articleBriefList) {
            document = ArticleDetailParser.getArticleDetailHtml(articleBrief.getDetailPath());
            if (document.text().contains("验证码")) {
                System.err.println("验证码大哥来了");
                break;
            }

        }
    }
}
