package com.example.springboot.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 书籍页面请求类，继承BaseRequest
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BookPageRequest extends BaseRequest {

    /**
     * 图书名称。
     */
    private String name;

    /**
     * 图书作者。
     */
    private String author;

    /**
     * 图书编号。
     */
    private String bookNo;
}
