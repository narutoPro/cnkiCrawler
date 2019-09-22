package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: chende
 * @date: 2019/7/25 09:41
 * @description: 文章详情
 */
@Data
@Entity
@Table
public class ArticleDetail {
    public ArticleDetail(){}
    public ArticleDetail(Long acticleBriefId){
        this.acticleBriefId=acticleBriefId;
    }

    @Id
    @GeneratedValue
    private Long id;

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

    /**
     * 对应文章brief的ID
     */
    Long acticleBriefId;



}
