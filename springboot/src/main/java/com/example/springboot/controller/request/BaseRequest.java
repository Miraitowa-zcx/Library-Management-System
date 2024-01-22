package com.example.springboot.controller.request;

import lombok.Data;

/**
 * 基本请求类
 *
 * @author 20383
 */
@Data
public class BaseRequest {

    /**
     * 页码，默认为1
     */
    private Integer pageNum = 1;

    /**
     * 每页大小，默认为10
     */
    private Integer pageSize = 10;
}
