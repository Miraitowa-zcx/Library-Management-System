package com.example.springboot.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户页面请求类，继承BaseRequest
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest extends BaseRequest {

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机号
     */
    private String phone;
}
