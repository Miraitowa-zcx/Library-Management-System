package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 书籍服务实现类，实现IBookService接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Slf4j
@Service
public class BookService implements IBookService {

    @Resource
    BookMapper bookMapper;

    /**
     * 全部查询
     *
     * @return 书籍列表
     */
    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    /**
     * 模糊分页查询
     *
     * @param baseRequest 基本请求对象
     * @return 分页信息
     */
    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Book> books = bookMapper.listByCondition(baseRequest);
        return new PageInfo<>(books);
    }

    /**
     * 添加书籍
     *
     * @param book 书籍对象
     */
    @Override
    public void save(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            bookMapper.save(book);
        } catch (Exception e) {
            throw new RuntimeException("添加失败", e);
        }
    }

    /**
     * 根据ID查找书籍
     *
     * @param id 书籍ID
     * @return 书籍对象
     */
    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    /**
     * 更新书籍信息
     *
     * @param book 书籍对象
     */
    @Override
    public void update(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            book.setUpdatetime(new Timestamp(new Date().getTime()));
            bookMapper.updateById(book);
        } catch (Exception e) {
            throw new RuntimeException("编辑失败", e);
        }
    }

    /**
     * 根据ID删除书籍
     *
     * @param id 书籍ID
     */
    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    /**
     * 获取分类名称
     *
     * @param categories 书籍分类列表
     * @return 分类名称
     */
    private String category(List<String> categories) {
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)) {
            categories.forEach(v -> sb.append(v).append(" > "));
            return sb.substring(0, sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }
}
