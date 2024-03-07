package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
public interface IUserService {

    /**
     * 获取用户列表
     */
    List<User> list();

    /**
     * 分页获取用户列表
     *
     * @param baseRequest 基本请求对象
     * @return 分页对象
     */
    PageInfo<User> page(BaseRequest baseRequest);

    /**
     * 保存用户
     *
     * @param user 用户对象
     */
    void save(User user);

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    User getById(Integer id);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     */
    void update(User user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    void deleteById(Integer id);

    /**
     * 处理用户账号
     *
     * @param user 用户对象
     */
    void handleAccount(User user);
}

