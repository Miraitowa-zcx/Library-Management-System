package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据映射器接口
 *
 * @author 20383
 */
@Mapper
public interface UserMapper {

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    List<User> list();

    /**
     * 根据条件查询用户列表
     *
     * @param baseRequest 查询条件
     * @return 用户列表
     */
    List<User> listByCondition(BaseRequest baseRequest);

    /**
     * 添加用户
     *
     * @param user 待添加的用户
     */
    void save(User user);

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getById(Integer id);

    /**
     * 根据ID更新用户信息
     *
     * @param user 待更新的用户
     */
    void updateById(User user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    void deleteById(Integer id);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);
}
