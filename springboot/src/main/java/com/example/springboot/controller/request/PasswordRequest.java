package com.example.springboot.controller.request;

import lombok.Data;

/**
 * 密码修改请求类
 *
 * @author 20383
 */
@Data
public class PasswordRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 新密码
     */
    private String newPass;
}
