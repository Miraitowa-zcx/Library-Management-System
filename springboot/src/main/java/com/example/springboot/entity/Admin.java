package com.example.springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员实体类，继承BaseEntity
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Admin extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private boolean status;
}
