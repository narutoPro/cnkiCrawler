package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: chende
 * @date: 2019/7/23 22:08
 * @description:
 */


@Data
@Entity
@Table(name = "searchPage")
public class SearchPage {

    @Id
    @GeneratedValue
    private Long id;

    /*
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String words;
     */

    @Column(columnDefinition ="mediumtext")
    private String html;

    //分类号
    private String clc;

    //当前页数
    private Integer curpage;

    private Boolean isParsed;


}
