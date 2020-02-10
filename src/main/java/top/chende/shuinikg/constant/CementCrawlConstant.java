package top.chende.shuinikg.constant;

/**
 * @author: chende
 * @date: 2019/12/3 14:22
 * @description:
 */


public class CementCrawlConstant {

    /**
     * redis set缓存已经访问的url的key
     */
    public static  final  String REDIS_VISTID_URL_KEY="visited_url";

    public static final String REDIS_URL_KEY="url";

    //设置URL正则表达式
    public static final String URL_MATCH_REGEX = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
            + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";

    public static final int  NUM_STORE_DB=20;
}
