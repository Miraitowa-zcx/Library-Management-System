package com.example.springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 类型：分类
 * 用于表示系统的分类信息，继承BaseEntity
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Category extends BaseEntity {
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类描述
     */
    private String remark;
    /**
     * 父级分类ID
     */
    private Integer pid;

    /**
     * 子分类列表
     */
    private List<Category> children;
}
