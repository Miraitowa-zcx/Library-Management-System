package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 借阅服务实现类，实现IBorrowService接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Slf4j
@Service
public class BorrowService implements IBorrowService {

    // 用户mapper
    private final UserMapper userMapper;
    // 书籍mapper
    private final BookMapper bookMapper;

    /**
     * 构造方法，注入用户mapper和书籍mapper
     *
     * @param userMapper 用户mapper接口实例
     * @param bookMapper 书籍mapper接口实例
     */
    @Autowired
    public BorrowService(UserMapper userMapper, BookMapper bookMapper) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    // 借阅mapper
    @Resource
    BorrowMapper borrowMapper;

    /**
     * 全部查询，获取所有借阅信息
     *
     * @return 借阅列表
     */
    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    /**
     * 模糊分页查询，获取借阅信息的分页结果
     *
     * @param baseRequest 分页请求对象
     * @return 借阅信息的分页结果
     */
    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);
        for (Borrow borrow : borrows) {
            // 类型转化
            LocalDate returnDate = borrow.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(returnDate)) {
                borrow.setNote("即将到期");
            } else if (now.isEqual(returnDate)) {
                borrow.setNote("已到期");
            } else if (now.isAfter(returnDate)) {
                borrow.setNote("已逾期");
            } else {
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }

    /**
     * 添加借阅信息，插入借阅记录并更新用户和书籍信息
     *
     * @param borrow 借阅信息
     */
    @Override
    @Transactional
    public void save(Borrow borrow) {
        String userNo = borrow.getUserNo();
        User user = userMapper.getByUsername(userNo);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
        Book book = bookMapper.getByNo(borrow.getBookNo());
        if (Objects.isNull(book)) {
            throw new ServiceException("所借图书不存在");
        }
        if (book.getNums() < 1) {
            throw new ServiceException("图书已借完");
        }
        Integer account = user.getAccount();
        Integer score = book.getScore() * borrow.getDays();
        if (score > account) {
            throw new ServiceException("积分不足");
        }

        // 更新余额和图书数量
        user.setAccount(user.getAccount() - score);
        userMapper.updateById(user);
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);

        // 新增借书记录
        borrow.setReturnDate(new Timestamp(new Date().getTime() + 24L * 60 * 60 * 1000 * borrow.getDays()));
        borrow.setScore(score);
        borrowMapper.save(borrow);
    }

    /**
     * 根据ID查找借阅信息
     *
     * @param id 借阅ID
     * @return 借阅信息
     */
    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    /**
     * 更新借阅信息
     *
     * @param borrow 借阅信息
     */
    @Override
    public void update(Borrow borrow) {
        borrow.setUpdatetime(new Timestamp(new Date().getTime()));
        borrowMapper.updateById(borrow);
    }

    /**
     * 根据ID删除借阅信息
     *
     * @param id 借阅ID
     */
    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }
}
