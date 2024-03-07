package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类控制器
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    /**
     * 构造函数，注入分类服务
     * @param categoryService 分类服务
     */
    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 添加分类信息
     *
     * @param category 分类信息
     * @return 分类信息
     */
    @PostMapping("/save")
    public Result save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    /**
     * 更新分类信息
     *
     * @param category 分类信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success();
    }

    /**
     * 根据ID删除分类信息
     *
     * @param id 分类ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID查询分类信息
     *
     * @param id 分类ID
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 全部查询
     *
     * @return 查询结果
     */
    @GetMapping("/list")
    public Result list() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    /**
     * 树形结构查询
     *
     * @return 查询结果
     */
    @GetMapping("/tree")
    public Result tree() {
        List<Category> list = categoryService.treeList();
        return Result.success(createTree(null, list));
    }

    /**
     * 完全递归方法
     *
     * @param pid 父ID
     * @param categories 分类列表
     * @return 树形结构列表
     */
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories) {
            if (pid == null) {
                if (category.getPid() == null) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            } else {
                if (pid.equals(category.getPid())) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())) {
                category.setChildren(null);
            }
        }
        return treeList;
    }

    /**
     * 模糊分页查询
     *
     * @param categoryPageRequest 分页请求对象
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest) {
        return Result.success(categoryService.page(categoryPageRequest));
    }
}
