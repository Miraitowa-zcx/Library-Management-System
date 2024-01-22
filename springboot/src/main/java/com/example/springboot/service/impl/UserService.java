package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类，实现 IUserService 接口
 *
 * @author 20383
 */
@Service
public class UserService implements IUserService {

    private final UserMapper userMapper;

    /**
     * 构造方法，注入用户mapper
     *
     * @param userMapper 用户mapper
     */
    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 列表查询所有用户
     *
     * @return 用户列表
     */
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    /**
     * 模糊分页查询
     *
     * @param baseRequest 基本请求对象
     * @return 分页信息
     *
     */
    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

    /**
     * 添加用户
     *
     * @param user 用户对象
     */
    @Override
    public void save(User user) {
        // 开卡日期
        Date date = new Date();
        // 卡号
        user.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        userMapper.save(user);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户 ID
     * @return 用户对象
     */
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     */
    @Override
    public void update(User user) {
        user.setUpdatetime(new Timestamp(new Date().getTime()));
        userMapper.updateById(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户 ID
     */
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    /**
     * 处理账户信息
     *
     * @param user 用户对象
     */
    @Override
    public void handleAccount(User user) {
        Integer score = user.getScore();
        if (score == null) {
            return;
        }
        Integer id = user.getId();
        User dbUser = userMapper.getById(id);
        dbUser.setAccount(dbUser.getAccount() + score);
        userMapper.updateById(dbUser);
    }
}
