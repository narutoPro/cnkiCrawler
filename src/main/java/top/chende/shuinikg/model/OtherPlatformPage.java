package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: chende
 * @date: 2019/11/18 10:22
 * @description:
 */
@Data
@Entity
//@Table
public class OtherPlatformPage {

    @Id
    @GeneratedValue
    private Long id;


    /**
     * 网页地址
     */
    String url;

    /**
     *网页类型
     */
    String type;
    /**
     *网页html文本
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text", nullable = true)
    String html;



}
