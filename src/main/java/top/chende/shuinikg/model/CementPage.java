package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: chende
 * @date: 2019/12/3 10:36
 * @description:  爬取水泥网数据对应的实体类
 */


@Data
@Entity
@Table(name="cementpage" ,
indexes = {  @Index(name = "url_index",columnList = "url",unique = false)}
)
public class CementPage {
    @Id
    @GeneratedValue
    private Long id;


    private String url;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "mediumtext",nullable = true)
    private String html;

}
