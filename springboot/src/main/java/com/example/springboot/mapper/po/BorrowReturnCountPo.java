package com.example.springboot.mapper.po;

import lombok.Data;

/**
 * 借还数量PO类
 *
 * @author 20383
 */
@Data
public class BorrowReturnCountPo {
    /**
     * 借还数量PO类
     */
    private String date;  // 借还日期
    private Integer count;  // 借还数量
}

