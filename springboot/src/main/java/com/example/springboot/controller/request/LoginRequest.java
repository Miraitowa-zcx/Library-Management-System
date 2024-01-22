package com.example.springboot.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录请求实体
 *
 * @author 20383
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
