package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.ReturnPageRequest;
import com.example.springboot.entity.Return;
import com.example.springboot.service.impl.ReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 还书控制器
 *
 * @author 20383
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/return")
public class ReturnController {

    private final ReturnService returnService;

    /**
     * 构造函数，注入还书服务
     *
     * @param returnService 还书服务
     */
    @Autowired
    private ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    /**
     * 模糊分页查询
     *
     * @param returnPageRequest 分页请求对象
     * @return 查询结果
     */
    @GetMapping("/page")
    public Result page(ReturnPageRequest returnPageRequest) {
        return Result.success(returnService.page(returnPageRequest));
    }

    /**
     * 添加还书信息
     *
     * @param returnEntity 还书信息
     * @return 还书信息
     */
    @PostMapping("/save")
    public Result save(@RequestBody Return returnEntity) {
        returnService.save(returnEntity);
        return Result.success();
    }

    /**
     * 根据ID删除还书信息
     *
     * @param id 还书ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        returnService.deleteById(id);
        return Result.success();
    }
}
