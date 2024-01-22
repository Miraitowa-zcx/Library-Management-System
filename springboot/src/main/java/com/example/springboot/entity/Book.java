package com.example.springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 书籍实体类，继承BaseEntity
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Book extends BaseEntity {

    /**
     * 书籍名称
     */
    private String name;

    /**
     * 书籍描述
     */
    private String description;

    /**
     * 出版日期
     */
    private String publishDate;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 分类
     */
    private String category;

    /**
     * 书籍编号
     */
    private String bookNo;

    /**
     * 封面
     */
    private String cover;

    /**
     * 类别列表
     */
    private List<String> categories;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 数量
     */
    private Integer nums;
}
