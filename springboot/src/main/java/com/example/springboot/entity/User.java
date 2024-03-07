package com.example.springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类，继承BaseEntity
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 账号
     */
    private Integer account;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 状态
     */
    private Boolean status;
}
