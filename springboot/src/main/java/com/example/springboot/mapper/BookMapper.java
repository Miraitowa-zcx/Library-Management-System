package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 书籍映射器接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Mapper
public interface BookMapper {

    /**
     * 获取所有书籍列表
     *
     * @return 所有书籍列表
     */
    List<Book> list();

    /**
     * 根据条件获取书籍列表
     *
     * @param baseRequest 条件
     * @return 书籍列表
     */
    List<Book> listByCondition(BaseRequest baseRequest);

    /**
     * 添加书籍
     *
     * @param book 书籍对象
     */
    void save(Book book);

    /**
     * 根据ID获取书籍
     *
     * @param id ID
     * @return 书籍对象
     */
    Book getById(Integer id);

    /**
     * 根据ID更新书籍
     *
     * @param book 书籍对象
     */
    void updateById(Book book);

    /**
     * 根据ID删除书籍
     *
     * @param id ID
     */
    void deleteById(Integer id);

    /**
     * 根据编号获取书籍
     *
     * @param bookNo 编号
     * @return 书籍对象
     */
    Book getByNo(String bookNo);

    /**
     * 根据编号更新书籍数量
     *
     * @param bookNo 编号
     */
    void updateNumByNo(String bookNo);
}
