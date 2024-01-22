package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.service.IBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 借阅控制器
 *
 * @author 20383
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    private final IBorrowService borrowService;

    /**
     * 构造函数，注入借阅服务
     *
     * @param borrowService 借阅服务
     */
    @Autowired
    public BorrowController(IBorrowService borrowService) {
        this.borrowService = borrowService;
    }

    /**
     * 添加借阅信息
     *
     * @param borrow 借阅信息
     * @return 借阅信息
     */
    @PostMapping("/save")
    public Result save(@RequestBody Borrow borrow) {
        borrowService.save(borrow);
        return Result.success();
    }

    /**
     * 更新借阅信息
     *
     * @param borrow 借阅信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody Borrow borrow) {
        borrowService.update(borrow);
        return Result.success();
    }

    /**
     * 根据ID删除借阅信息
     *
     * @param id 借阅ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        borrowService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID查询借阅信息
     *
     * @param id 借阅ID
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Borrow borrow = borrowService.getById(id);
        return Result.success(borrow);
    }

    /**
     * 全部查询
     *
     * @return 查询结果
     */
    @GetMapping("/list")
    public Result list() {
        List<Borrow> list = borrowService.list();
        return Result.success(list);
    }

    /**
     * 模糊分页查询
     *
     * @param borrowPageRequest 分页请求对象
     * @return 查询结果
     */
    @GetMapping("/page")
    public Result page(BorrowPageRequest borrowPageRequest) {
        return Result.success(borrowService.page(borrowPageRequest));
    }
}
