package top.chende.shuinikg.crawl.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author: chende
 * @date: 2019/7/23 13:50
 * @description:
 */
public class JsoupConnectDemo {
    String url = "http://kns.cnki.net/kns/brief/brief.aspx?curpage=2&RecordsPerPage=20&QueryID=1&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx&isinEn=0&";
    String cookieValue = "LID=WEEvREcwSlJHSldRa1FhdkJkVG1BdXBpcngxUy85dFR3MkxXL1JUWUNlST0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4IQMovwHtwkF4VYPoHbKxJw!!; KNS_SortType=; _pk_ref=%5B%22%22%2C%22%22%2C1563850225%2C%22http%3A%2F%2Fwww.cnki.net%2F%22%5D; Ecp_session=1; Ecp_ClientId=2170809102402085029; SID_kcms=124118; SID_krsnew=125134; SID_crrs=125133; SID_klogin=125142; ASP.NET_SessionId=3wxy2o1cnxrqojtpvatjp2h0; SID_kns=123111; RsPerPage=20; cnkiUserKey=d4c719ec-8712-c0b4-297e-19cbc790f2b0";
    static String para1 = "https://kns.cnki.net/kns/brief/brief.aspx?RecordsPerPage=20&QueryID=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx&isinEn=1&";
    static String c2 = "ASP.NET_SessionId=szdxmanmd0adsiex4yakm0p4; Ecp_ClientId=3190723083400975069; SID_kns=123114; SID_klogin=125142; KNS_SortType=; RsPerPage=20; SID_krsnew=125132; cnkiUserKey=d0a78587-3d4b-5b76-698a-95637c2f99c0; SID_kcms=124105; SID_kinfo=125104; Ecp_lout=1; IsLogin=; Ecp_session=1; ASPSESSIONIDQSTDCTBD=KCINDAOCKHPFKOEHJEMCMCMD; _pk_ses=*; LID=WEEvREdxOWJmbC9oM1NjYkZCbDdrdXJMdC9GOWs2TlJyUm8xbzNYUTZIK1E=$R1yZ0H6jyaa0en3RxVUd8df-oHi7XMMDo7mtKT6mSmEvTuk11l2gFA!!; Ecp_LoginStuts={\"IsAutoLogin\":false,\"UserName\":\"HZ0001\",\"ShowName\":\"%e6%ad%a6%e6%b1%89%e7%90%86%e5%b7%a5%e5%a4%a7%e5%ad%a6\",\"UserType\":\"bk\",\"r\":\"OjUKm9\"}; c_m_LinID=LinID=WEEvREdxOWJmbC9oM1NjYkZCbDdrdXJMdC9GOWs2TlJyUm8xbzNYUTZIK1E=$R1yZ0H6jyaa0en3RxVUd8df-oHi7XMMDo7mtKT6mSmEvTuk11l2gFA!!&ot=07/24/2019 17:32:10; c_m_expire=2019-07-24 17:32:10";

    //                    .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
    public static void main(String[] args) {

        String userAg1="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.2 Safari/605.1.15";
        String userAg2="Mozilla/5.0 (Macintosh; Intel Mac OS X 11_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
        int netErrorCount = 0;
        int totalPages = 45;
        for (int curpage = 0; curpage <= totalPages; curpage++) {
            String usa=userAg1;
            Document document = null;
            try {
                document = Jsoup.connect(para1)
                        .header("Cookie", c2)
                        .data("curpage", curpage + "")
                        .header("User-Agent", usa)
                        .get();
                System.out.println("获取数据: " + curpage + " :");
                System.out.println(document.title());

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("网络出错第" + netErrorCount + "");
                break;
            }
            if (document.text().contains("请输入验证码")) {
                System.err.println("验证码大哥来了,page:" + curpage);

                  continue;
              //  break;
            }
            if (curpage % 10 == 0) {
                try {
                    System.out.println("睡眠10000ms");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    System.out.println("睡眠1000ms");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//            Document document2=Jsoup.connect(para1)
//                    .header("Cookie", c2)
//                    .header("Connection","keep-alive")
//                    .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
//                    .data("curpage", 2 + "")
//                    .get();
        //    System.out.println(document.text());

    }
}
