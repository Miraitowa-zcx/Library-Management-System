package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 借阅实体类，继承BaseEntity
 *
 * @author 20383
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Borrow extends BaseEntity {

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
     * 用户手机号
     */
    private String userPhone;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 状态
     */
    private String status;

    /**
     * 借阅天数
     */
    private Integer days;

    /**
     * 还书日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date returnDate;

    /**
     * 提醒还书
     */
    private String note;
}
