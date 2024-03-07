package com.example.springboot.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 借阅页面请求类，继承BaseRequest
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BorrowPageRequest extends BaseRequest {

    /**
     * 书籍名称
     */
    private String bookName;

    /**
     * 书籍编号
     */
    private String bookNo;

    /**
     * 用户名称
     */
    private String userName;
}
