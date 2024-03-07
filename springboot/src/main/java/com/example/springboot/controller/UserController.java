package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    /**
     * 构造函数，注入用户服务
     *
     * @param userService 用户服务
     */
    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 添加结果
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    /**
     * 处理用户账户
     *
     * @param user 用户信息
     * @return 处理结果
     */
    @PostMapping("/account")
    public Result account(@RequestBody User user) {
        userService.handleAccount(user);
        return Result.success();
    }

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 获取的用户信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 获取用户列表
     *
     * @return 用户信息列表
     */
    @GetMapping("/list")
    public Result list() {
        List<User> users = userService.list();
        return Result.success(users);
    }

    /**
     * 获取用户分页信息
     *
     * @param userPageRequest 用户分页请求信息
     * @return 用户分页信息
     */
    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(userService.page(userPageRequest));
    }
}
