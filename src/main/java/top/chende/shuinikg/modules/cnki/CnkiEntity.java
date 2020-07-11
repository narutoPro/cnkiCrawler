package top.chende.shuinikg.modules.cnki;


import lombok.Data;

import javax.persistence.*;

/**
 * 可能部分实际数据字段 长度超过
 */
@Data
@Entity
@Table(name = "cnki_entity")
public class CnkiEntity {

    @Id
    @GeneratedValue
    private Long id;

    /** 数据采集时间*/
    String collectDate;

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
     * 关键词
     */
    private String keyWords;

    String doi;

    /**
     * 中图分类号
     */
    String clc;
    /**
     * 作者单位
     */
    String orgn;

    //catalog_TUTOR 导师
    String tutor;

    //catalog_FUND 基金
    String fund;
    /**
     * 摘要
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition ="text",nullable=true)
    String articleAbstract;

    /** 详情页图片地址*/
    String paperImageUrls;


}
