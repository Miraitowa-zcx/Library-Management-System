package com.example.springboot.mapper.po;

import lombok.Data;

/**
 * 借还数量PO类
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Data
public class BorrowReturnCountPo {

    /**
     * 借还日期
     */
    private String date;

    /**
     * 借还数量
     */
    private Integer count;
}

