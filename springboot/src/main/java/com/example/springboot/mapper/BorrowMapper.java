package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 借阅映射器接口
 *
 * @author 20383
 */
@Mapper
public interface BorrowMapper {

    /**
     * 获取所有借阅列表
     *
     * @return 所有借阅列表
     */
    List<Borrow> list();

    /**
     * 根据条件获取借阅列表
     *
     * @param baseRequest 条件
     * @return 借阅列表
     */
    List<Borrow> listByCondition(BaseRequest baseRequest);

    /**
     * 添加借阅信息
     *
     * @param borrow 借阅信息
     */
    void save(Borrow borrow);

    /**
     * 根据ID获取借阅信息
     *
     * @param id ID
     * @return 借阅信息
     */
    Borrow getById(Integer id);

    /**
     * 根据ID更新借阅信息
     *
     * @param borrow 借阅信息
     */
    void updateById(Borrow borrow);

    /**
     * 根据ID删除借阅信息
     *
     * @param id ID
     */
    void deleteById(Integer id);

    /**
     * 更新借阅状态
     *
     * @param status 状态
     * @param id     ID
     */
    void updateStatus(String status, Integer id);
}
