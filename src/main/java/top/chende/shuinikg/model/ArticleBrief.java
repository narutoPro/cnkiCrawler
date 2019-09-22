package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: chende
 * @date: 2019/7/24 09:03
 * @description:
 */

@Data
@Entity
@Table
public class ArticleBrief {

    public  ArticleBrief( ) {
    }

    public  ArticleBrief(Long id, String detailPath) {
        this.id = id;
        this.detailPath = detailPath;
    }

    @Id
    @GeneratedValue
    private Long id;

    //题名
    String title;

    //作者
    String author;

    //来源  例如：中国建材报 硅酸盐通报 浙江大学
    String resource;

    //发表时间
    String publishDate;

    //数据库 （值为： 期刊、硕士、国际会议、中国会员、博士、报纸）
    String db;

    /**
     * 被引次数
     */
    Integer indexed;

    /**
     * 被下载次数
     */
    Integer downloaded;
    /**
     * 下载地址
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text", nullable = true)
    String downloadPath;
    /**
     * 文章详情地址
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text", nullable = true)
    String detailPath;

    // Boolean isParsed;
    /**
     * 文章中图分类号
     * 代码已经搞了，在加比较麻烦
     */
    //String clc;

}
