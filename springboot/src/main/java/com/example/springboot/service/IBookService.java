package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 书籍服务接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
public interface IBookService {

    /**
     * 全部查询
     *
     * @return 所有书籍的列表
     */
    List<Book> list();

    /**
     * 自关联查询
     *
     * @param baseRequest 分页请求对象
     * @return 分页后的书籍信息
     */
    PageInfo<Book> page(BaseRequest baseRequest);

    /**
     * 添加
     *
     * @param book 待添加的书籍对象
     */
    void save(Book book);

    /**
     * 根据ID查找
     *
     * @param id 书籍ID
     * @return 对应的书籍对象
     */
    Book getById(Integer id);

    /**
     * 更新信息
     *
     * @param book 待更新的书籍对象
     */
    void update(Book book);

    /**
     * 根据ID删除
     *
     * @param id 书籍ID
     */
    void deleteById(Integer id);
}
