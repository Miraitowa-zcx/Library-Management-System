package com.example.springboot.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 还书页面请求，继承BaseRequest
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReturnPageRequest extends BaseRequest {

    /**
     * 书籍名称
     */
    private String bookName;

    /**
     * 书籍编号
     */
    private String bookNo;

    /**
     * 用户名
     */
    private String userName;
}
