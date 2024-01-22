package com.example.springboot.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类页面请求类，继承BaseRequest
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryPageRequest extends BaseRequest {

    /**
     * 分类名称
     */
    private String name;
}
