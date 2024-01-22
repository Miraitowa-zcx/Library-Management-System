package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分类映射器接口
 *
 * @author 20383
 */
@Mapper
public interface CategoryMapper {

    /**
     * 获取所有分类列表
     *
     * @return 所有分类列表
     */
    List<Category> list();

    /**
     * 根据条件获取分类列表
     *
     * @param baseRequest 条件
     * @return 符合条件的分类列表
     */
    List<Category> listByCondition(BaseRequest baseRequest);

    /**
     * 添加分类
     *
     * @param category 待添加的分类
     */
    void save(Category category);

    /**
     * 根据ID获取分类
     *
     * @param id 分类的ID
     * @return 对应的分类对象
     */
    Category getById(Integer id);

    /**
     * 根据ID更新分类
     *
     * @param category 待更新的分类
     */
    void updateById(Category category);

    /**
     * 根据ID删除分类
     *
     * @param id 分类的ID
     */
    void deleteById(Integer id);

    /**
     * 获取分类树列表
     *
     * @return 分类树列表
     */
    List<Category> treeList();
}
