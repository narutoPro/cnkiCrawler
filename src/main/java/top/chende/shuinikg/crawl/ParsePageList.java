package top.chende.shuinikg.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.util.ObjPropertyPrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chende
 * @date: 2019/7/24 23:58
 * @description:
 */
public class ParsePageList {

    static String listClass = "GridTableContent";

    public static List<ArticleBrief> parser(String html) {

        List<ArticleBrief> articleBriefs=new ArrayList<>();
        Document document = Jsoup.parse(html);
        //获取文章列表元素
        Element table = document.getElementsByClass(listClass).first();
        List<Element> elements = table.getElementsByTag("tr");
        for (int i = 1; i < elements.size(); i++) {
            //System.out.println("#######解析第   " + i + "  行数据########");
            articleBriefs.add(parser2ArticleBrief(elements.get(i)));
            //System.out.println("#################################");
        }
        return articleBriefs;
    }


    /**
     * 解析列表标签<TR> 里的元素
     * <p>
     * html() 和 outerhtml() 的区别：第5个元素html ：期刊
     * 第5个元素outhtml ：<td align="center"> 期刊 </td>
     *
     * @param tr
     * @return
     */
    public static ArticleBrief parser2Articlebri(Element tr) {
        Elements tds = tr.getElementsByTag("td");
        for (int i = 1; i < tds.size(); i++) {
            Element td = tds.get(i);
            //     System.out.println("第" + i + "个元素html ：" + td.html());
            System.out.println("第" + i + "个元素outhtml ：" + td.outerHtml());

        }
        return null;
    }

    /**
     *
     * @param tr
     * @return
     */
    public static ArticleBrief parser2ArticleBrief(Element tr) {
        Elements tds = tr.getElementsByTag("td");
        ArticleBrief articleBrief=new ArticleBrief();

        // 1.标题
        Element titleE=tds.get(1);
        String artiledetailPath=titleE.getElementsByTag("a").attr("href");
        String title=titleE.text();
       // System.out.println(title+artiledetailPath);

        articleBrief.setTitle(title);
        articleBrief.setDetailPath(artiledetailPath);
        // 2.作者
        String author=tds.get(2).text();
        // 作者长度太长
        if (author.length()>245)
            author=author.substring(0,245);
        articleBrief.setAuthor(author);



        //3.来源  江西建材
        articleBrief.setResource(tds.get(3).text());

        //4.发表时间
        articleBrief.setPublishDate(tds.get(4).text());

        //5.db
        articleBrief.setDb(tds.get(5).text());

        //6.被引次数  null或者""
        String indexedStr=tds.get(6).text();
        Integer index;
        if (indexedStr==null || indexedStr.equals(""))
            index=0;
        else
            index=new Integer(indexedStr);
        articleBrief.setIndexed(index);

        //7.下载次数
        String downloadStr=tds.get(7).text();
        Integer downloaded;
        if (downloadStr==null || downloadStr.equals(""))
            downloaded=0;
        else
            downloaded=new Integer(downloadStr);
        articleBrief.setDownloaded(downloaded);

        String downloadPath=tds.get(7).getElementsByTag("a").attr("href");
                //.attr("href");
        articleBrief.setDownloadPath(downloadPath);

        System.out.println(ObjPropertyPrintUtil.toString(articleBrief,ArticleBrief.class));
        return articleBrief;
    }

}
/*
*
#######解析第   16  行数据########
第1个元素outhtml ：<td> <a class="fz14" href="/kns/detail/detail.aspx?QueryID=59&amp;CurRec=16&amp;recid=&amp;FileName=SNIZ201902004&amp;DbName=CJFDLAST2019&amp;DbCode=CJFQ&amp;yx=&amp;pr=&amp;URLID=&amp;bsm=QK0202;" target="_blank">协同处置危废对烧成系统的影响</a> </td>
第2个元素outhtml ：<td class="author_flag"> <a href="javascript:void(0)">单锋</a>; <a href="javascript:void(0)">王俊峰</a> </td>
第3个元素outhtml ：<td> <a target="_blank" href="/kns/NaviBridge.aspx?bt=1&amp;DBCode=CJFD&amp;BaseID=SNIZ&amp;UnitCode=&amp;NaviLink=%e6%b0%b4%e6%b3%a5">水泥</a> </td>
第4个元素outhtml ：<td align="center"> 2019-02-10 </td>
第5个元素outhtml ：<td align="center"> 期刊 </td>
第6个元素outhtml ：<td align="right"> </td>
第7个元素outhtml ：<td align="center"> <a href="../download.aspx?filename=tFDMw0WaXVXdJ52LUVjZkFTWhVjc09UbBdTbBNHV4F3UzFFZxMUdrEFbvA1RKdFOyE1S6pHZrJnW6RlR3RFVOFzZodEaidWayUHNnd1TBl2Q3VkdOJDRRJXV5RHayVTSzB1c3NFVwlVb0xmZalkTnpETVhFbYNjb&amp;tablename=CJFDLAST2019" target="_blank" class="briefDl_D" title="下载_4页"></a> <span class="downloadCount"><a href="javascript:void(0);" onclick="SubCountDownLoad(0,this)">26</a></span> </td>
第8个元素outhtml ：<td align="center"> <a class="kreader kreader1 xml" target="online_open" href="/kns/ReadRedirectPage.aspx?flag=html&amp;domain=%2fKXReader%2fDetail%3fdbcode%3dCJFQ%26filename%3dSNIZ201902004" title="HTML阅读"></a> </td>
第9个元素outhtml ：<td align="center"> <a href="javascript:void(0);" onclick="AddFavToKpc(this); return false;" title="收藏" class="star"> 收藏</a> </td>
#################################
#######解析第   17  行数据########
第1个元素outhtml ：<td> <a class="fz14" href="/kns/detail/detail.aspx?QueryID=59&amp;CurRec=17&amp;recid=&amp;FileName=SNIZ201902005&amp;DbName=CJFDLAST2019&amp;DbCode=CJFQ&amp;yx=&amp;pr=&amp;URLID=&amp;bsm=QK0202;" target="_blank">4500t/d生产线预热器降阻技术改造</a> </td>
第2个元素outhtml ：<td class="author_flag"> <a href="javascript:void(0)">刘明伟</a>; <a href="javascript:void(0)">黎奉武</a>; <a href="javascript:void(0)">彭涛</a> </td>
第3个元素outhtml ：<td> <a target="_blank" href="/kns/NaviBridge.aspx?bt=1&amp;DBCode=CJFD&amp;BaseID=SNIZ&amp;UnitCode=&amp;NaviLink=%e6%b0%b4%e6%b3%a5">水泥</a> </td>
第4个元素outhtml ：<td align="center"> 2019-02-10 </td>
第5个元素outhtml ：<td align="center"> 期刊 </td>
第6个元素outhtml ：<td align="right"> </td>
第7个元素outhtml ：<td align="center"> <a href="../download.aspx?filename=tFDMw0WaXVXdJ52LUVjZkFTWhVjc09UbBdTbBNHV4F3UzFFZxMUdrEFbvA1RKdFOyE1S6pHZrJnW6RlR1MFWKFzZodEaidWayUHNnd1TBl2Q3VkdOJDRRJXV5RHayVTSzB1c3NFVwlVb0xmZalkTnpETVhFbYNjb&amp;tablename=CJFDLAST2019" target="_blank" class="briefDl_D" title="下载_2页"></a> <span class="downloadCount"><a href="javascript:void(0);" onclick="SubCountDownLoad(0,this)">9</a></span> </td>
第8个元素outhtml ：<td align="center"> <a class="kreader kreader1 xml" target="online_open" href="/kns/ReadRedirectPage.aspx?flag=html&amp;domain=%2fKXReader%2fDetail%3fdbcode%3dCJFQ%26filename%3dSNIZ201902005" title="HTML阅读"></a> </td>
第9个元素outhtml ：<td align="center"> <a href="javascript:void(0);" onclick="AddFavToKpc(this); return false;" title="收藏" class="star"> 收藏</a> </td>
#################################
#######解析第   18  行数据########
第1个元素outhtml ：<td> <a class="fz14" href="/kns/detail/detail.aspx?QueryID=59&amp;CurRec=18&amp;recid=&amp;FileName=ZJZB201902024&amp;DbName=CJFDLAST2019&amp;DbCode=CJFQ&amp;yx=&amp;pr=&amp;URLID=&amp;bsm=QK0503;" target="_blank">鑫塔水泥公司回转窑技术改造及调试总结</a> </td>
第2个元素outhtml ：<td class="author_flag"> <a class="KnowledgeNetLink" href="/kns/popup/knetsearchNew.aspx?sdb=CJFQ&amp;sfield=%e4%bd%9c%e8%80%85&amp;skey=%e5%a7%9c%e6%96%87%e4%b8%be&amp;scode=12607189&amp;acode=12607189" target="knet">姜文举</a>; <a href="javascript:void(0)">李传山</a> </td>
第3个元素outhtml ：<td> <a target="_blank" href="/kns/NaviBridge.aspx?bt=1&amp;DBCode=CJFD&amp;BaseID=ZJZB&amp;UnitCode=&amp;NaviLink=%e4%b8%ad%e5%9b%bd%e6%b0%b4%e6%b3%a5">中国水泥</a> </td>
第4个元素outhtml ：<td align="center"> 2019-02-01 </td>
第5个元素outhtml ：<td align="center"> 期刊 </td>
第6个元素outhtml ：<td align="right"> </td>
第7个元素outhtml ：<td align="center"> <a href="../download.aspx?filename=tFDMw0WaXVXdJ52LUVjZkFTWhVjc09UbBdTbBNHV4F3UzFFZxMUdrEFbvA1RKdFOyE1S6pHZrJnW6RlRUNEdTV0VjVDTql3ZVRlY3Z1biFVSsNTaolXcKRXV5RHayVTSzB1c3NFVwlVb0xmZalkTnpETVhFbYNjb&amp;tablename=CJFDLAST2019" target="_blank" class="briefDl_D" title="下载_2页"></a> <span class="downloadCount"><a href="javascript:void(0);" onclick="SubCountDownLoad(0,this)">20</a></span> </td>
第8个元素outhtml ：<td align="center"> <a class="kreader kreader1 xml" target="online_open" href="/kns/ReadRedirectPage.aspx?flag=html&amp;domain=%2fKXReader%2fDetail%3fdbcode%3dCJFQ%26filename%3dZJZB201902024" title="HTML阅读"></a> </td>
第9个元素outhtml ：<td align="center"> <a href="javascript:void(0);" onclick="AddFavToKpc(this); return false;" title="收藏" class="star"> 收藏</a> </td>
#################################
#######解析第   19  行数据########
第1个元素outhtml ：<td> <a class="fz14" href="/kns/detail/detail.aspx?QueryID=59&amp;CurRec=19&amp;recid=&amp;FileName=ZXDB201904073&amp;DbName=CJFDLAST2019&amp;DbCode=CJFQ&amp;yx=&amp;pr=&amp;URLID=&amp;bsm=QK07;" target="_blank">第四代篦冷机进风口的改造设计</a> </td>
第2个元素outhtml ：<td class="author_flag"> <a class="KnowledgeNetLink" href="/kns/popup/knetsearchNew.aspx?sdb=CJFQ&amp;sfield=%e4%bd%9c%e8%80%85&amp;skey=%e7%8e%8b%e8%be%89&amp;scode=41383389&amp;acode=41383389" target="knet">王辉</a> </td>
第3个元素outhtml ：<td> <a target="_blank" href="/kns/NaviBridge.aspx?bt=1&amp;DBCode=CJFD&amp;BaseID=ZXDB&amp;UnitCode=&amp;NaviLink=%e7%a7%91%e6%8a%80%e5%88%9b%e6%96%b0%e5%af%bc%e6%8a%a5">科技创新导报</a> </td>
第4个元素outhtml ：<td align="center"> 2019-02-01 </td>
第5个元素outhtml ：<td align="center"> 期刊 </td>
第6个元素outhtml ：<td align="right"> </td>
第7个元素outhtml ：<td align="center"> <a href="../download.aspx?filename=tFDMw0WaXVXdJ52LUVjZkFTWhVjc09UbBdTbBNHV4F3UzFFZxMUdrEFbvA1RKdFOyE1S6pHZrJnW6RlRvNnQIJUbCNWO50mM4UEOEJnUYpHSHNGdlJES2MHOZB1LUhEajpHdJpUMvlVb0xmZalkTnpETVhFbYNjb&amp;tablename=CJFDLAST2019" target="_blank" class="briefDl_D" title="下载_2页"></a> <span class="downloadCount"><a href="javascript:void(0);" onclick="SubCountDownLoad(0,this)">2</a></span> </td>
第8个元素outhtml ：<td align="center"> <a class="kreader kreader1 xml" target="online_open" href="/kns/ReadRedirectPage.aspx?flag=html&amp;domain=%2fKXReader%2fDetail%3fdbcode%3dCJFQ%26filename%3dZXDB201904073" title="HTML阅读"></a> </td>
第9个元素outhtml ：<td align="center"> <a href="javascript:void(0);" onclick="AddFavToKpc(this); return false;" title="收藏" class="star"> 收藏</a> </td>
#################################
#######解析第   20  行数据########
第1个元素outhtml ：<td> <a class="fz14" href="/kns/detail/detail.aspx?QueryID=59&amp;CurRec=20&amp;recid=&amp;FileName=JXJC201901063&amp;DbName=CJFDLAST2019&amp;DbCode=CJFQ&amp;yx=&amp;pr=&amp;URLID=&amp;bsm=QK0203;" target="_blank">水泥窑协同处置危废现状及发展趋势</a> </td>
第2个元素outhtml ：<td class="author_flag"> </td>
第3个元素outhtml ：<td> <a target="_blank" href="/kns/NaviBridge.aspx?bt=1&amp;DBCode=CJFD&amp;BaseID=JXJC&amp;UnitCode=&amp;NaviLink=%e6%b1%9f%e8%a5%bf%e5%bb%ba%e6%9d%90">江西建材</a> </td>
第4个元素outhtml ：<td align="center"> 2019-01-30 </td>
第5个元素outhtml ：<td align="center"> 期刊 </td>
第6个元素outhtml ：<td align="right"> </td>
第7个元素outhtml ：<td align="center"> <a href="../download.aspx?filename=tFDMw0WaXVXdJ52LUVjZkFTWhVjc09UbBdTbBNHV4F3UzFFZxMUdrEFbvA1RKdFOyE1S6pHZrJnW6RlRpV0Qk1WNOVkdxVTaz0GMlhUThtUO5gzawQWeYZESOtWYB9iTzB1c3NFVwlVb0xmZalkTnpETVhFbYNjb&amp;tablename=CJFDLAST2019" target="_blank" class="briefDl_D" title="下载_1页"></a> <span class="downloadCount"><a href="javascript:void(0);" onclick="SubCountDownLoad(0,this)">87</a></span> </td>
第8个元素outhtml ：<td align="center"> <a class="kreader kreader1 xml" target="online_open" href="/kns/ReadRedirectPage.aspx?flag=html&amp;domain=%2fKXReader%2fDetail%3fdbcode%3dCJFQ%26filename%3dJXJC201901063" title="HTML阅读"></a> </td>
第9个元素outhtml ：<td align="center"> <a href="javascript:void(0);" onclick="AddFavToKpc(this); return false;" title="收藏" class="star"> 收藏</a> </td>
#################################
*/
