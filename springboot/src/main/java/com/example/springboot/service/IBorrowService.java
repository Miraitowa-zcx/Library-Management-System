package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 借阅服务接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
public interface IBorrowService {

    /**
     * 全部查询
     *
     * @return 借阅信息的列表
     */
    List<Borrow> list();

    /**
     * 自关联查询
     *
     * @param baseRequest 分页请求对象
     * @return 分页后的借阅信息
     */
    PageInfo<Borrow> page(BaseRequest baseRequest);

    /**
     * 添加借阅信息
     *
     * @param borrow 待添加的借阅信息
     */
    void save(Borrow borrow);

    /**
     * 根据ID查找借阅信息
     *
     * @param id 借阅ID
     * @return 对应的借阅信息
     */
    Borrow getById(Integer id);

    /**
     * 更新借阅信息
     *
     * @param borrow 待更新的借阅信息
     */
    void update(Borrow borrow);

    /**
     * 根据ID删除借阅信息
     *
     * @param id 借阅ID
     */
    void deleteById(Integer id);
}
