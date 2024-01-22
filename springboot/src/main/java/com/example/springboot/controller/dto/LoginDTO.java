package com.example.springboot.controller.dto;

import lombok.Data;

/**
 * 登录DTO
 *
 * @author 20383
 */
@Data
public class LoginDTO {

    /**
     * ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * Token
     */
    private String token;
}
