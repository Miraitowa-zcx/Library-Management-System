package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Return;
import com.github.pagehelper.PageInfo;

/**
 * 还书服务接口
 *
 * @author 20383
 */
public interface IReturnService {

    /**
     * 分页模糊查询
     *
     * @param baseRequest 分页请求对象
     * @return 分页后的还书记录
     */
    PageInfo<Return> page(BaseRequest baseRequest);

    /**
     * 添加还书记录
     *
     * @param returnEntity 待添加还书记录
     */
    void save(Return returnEntity);

    /**
     * 根据ID删除还书记录
     *
     * @param id 还书ID
     */
    void deleteById(Integer id);
}
