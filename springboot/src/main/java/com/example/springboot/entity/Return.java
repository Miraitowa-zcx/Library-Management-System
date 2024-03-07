package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 还书实体类，继承BaseEntity
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Return extends BaseEntity {

    /**
     * 书籍名称
     */
    private String bookName;

    /**
     * 书籍编号
     */
    private String bookNo;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 成绩
     */
    private Integer score;

    /**
     * 状态
     */
    private String status;

    /**
     * 天数
     */
    private Integer days;

    /**
     * 还书日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date returnDate;

    /**
     * 实际日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp realDate;
}
