package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: chende
 * @date: 2019/7/27 10:41
 * @description: 分类
 */
@Data
@Entity
@Table
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 分类名称
     */
    private String cName;

    /**
     * 分类描述
     */
    private String cDescription;


    /**
     * 是某分类的子分类
     */
    private Long subCateOf;
}
