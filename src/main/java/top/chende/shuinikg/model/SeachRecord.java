package top.chende.shuinikg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: chende
 * @date: 2019/7/24 09:05
 * @description: 积累收集记录
 */

@Data
@Entity
@Table
public class SeachRecord {
    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition ="text")
    String url;

    String clc;

    @Column(columnDefinition ="text")
    String cookie;

    Long totalPages;
    Date date; //开始收集的时间

    public SeachRecord(String url, String clc, String cookie, Long totalPages) {
        this.url = url;
        this.clc = clc;
        this.cookie = cookie;
        this.totalPages = totalPages;
        this.date=new Date();
    }

    public static void main(String[] args) {
        System.out.println(new Date());
    }
}
