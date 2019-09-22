package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: chende
 * @date: 2019/7/21 10:07
 * @description: 术语,目前共计89条术语
 */

@Data
@Entity
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue
    private Long id;

    //术语名
    private String tName;

    //术语英文名称
    private String tEn;

    /**
     * 术语描述信息
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition ="text",nullable=true)
    private String tDescription;

    //术语分类
    private Long tCategory;

    //待补充信息

}
