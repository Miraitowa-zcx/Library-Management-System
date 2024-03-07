package com.example.springboot.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 书籍页面请求类，继承BaseRequest
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
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
