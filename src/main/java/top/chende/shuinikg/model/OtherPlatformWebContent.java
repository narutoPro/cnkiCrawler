package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: chende
 * @date: 2019/11/18 10:51
 * @description: OtherPlatformPage包含的文本内容
 */
@Data
@Entity
//@Table
public class OtherPlatformWebContent {
    @Id
    @GeneratedValue
    private Long id;

    //对应的OtherPlatformPage对应的id
    private Long otherPlatformPageId;

    //阅读数
    private Long readNum;

    //评论数
    private Long commentNum;

    //标题
    private String title;

    //内容
    private String content;

}
