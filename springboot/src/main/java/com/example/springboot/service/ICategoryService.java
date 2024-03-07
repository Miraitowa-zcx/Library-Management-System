package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分类服务接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
public interface ICategoryService {

    /**
     * 查询全部分类
     *
     * @return 所有分类列表
     */
    List<Category> list();

    /**
     * 分页查询分类
     *
     * @param baseRequest 分页请求对象
     * @return 分页信息
     */
    PageInfo<Category> page(BaseRequest baseRequest);

    /**
     * 添加分类
     *
     * @param category 待添加的分类对象
     */
    void save(Category category);

    /**
     * 根据ID查找分类
     *
     * @param id 分类ID
     * @return 对应的分类对象
     */
    Category getById(Integer id);

    /**
     * 更新分类信息
     *
     * @param category 待更新的分类对象
     */
    void update(Category category);

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     */
    void deleteById(Integer id);

    /**
     * 递归查询所有分类列表
     *
     * @return 所有分类列表
     */
    List<Category> treeList();
}
