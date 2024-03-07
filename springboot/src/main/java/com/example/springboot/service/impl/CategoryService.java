package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 分类服务实现类，实现ICategoryService接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Slf4j
@Service
public class CategoryService implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 全部查询
     *
     * @return 分类列表
     */
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    /**
     * 自关联查询
     *
     * @param baseRequest 请求参数
     * @return 分页信息
     */
    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Category> categories = categoryMapper.listByCondition(baseRequest);
        return new PageInfo<>(categories);
    }

    /**
     * 添加
     *
     * @param category 分类对象
     */
    @Override
    public void save(Category category) {
        categoryMapper.save(category);
    }

    /**
     * 根据ID查找
     *
     * @param id 分类ID
     * @return 分类对象
     */
    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    /**
     * 更新信息
     *
     * @param category 分类对象
     */
    @Override
    public void update(Category category) {
        category.setUpdatetime(new Timestamp(new Date().getTime()));
        categoryMapper.updateById(category);
    }

    /**
     * 根据ID删除
     *
     * @param id 分类ID
     */
    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    /**
     * 递归查询
     *
     * @return 分类树列表
     */
    @Override
    public List<Category> treeList() {
        return categoryMapper.treeList();
    }
}
