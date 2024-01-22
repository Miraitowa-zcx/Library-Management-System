package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Return;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.ReturnMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IReturnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * 还书服务实现类，实现IReturnService接口
 *
 * @author 20383
 */
@Slf4j
@Service
public class ReturnService implements IReturnService {

    private final ReturnMapper returnMapper;
    private final BookMapper bookMapper;
    private final BorrowMapper borrowMapper;
    private final UserMapper userMapper;

    /**
     * 构造函数，注入还书mapper、用户mapper、书籍mapper和借阅mapper
     *
     * @param returnMapper 还书Mapper接口实例
     * @param bookMapper   书籍Mapper接口实例
     * @param borrowMapper 借阅Mapper接口实例
     * @param userMapper   用户Mapper接口实例
     */
    @Autowired
    public ReturnService(ReturnMapper returnMapper, BookMapper bookMapper, BorrowMapper borrowMapper, UserMapper userMapper) {
        this.returnMapper = returnMapper;
        this.bookMapper = bookMapper;
        this.borrowMapper = borrowMapper;
        this.userMapper = userMapper;
    }

    /**
     * 模糊分页查询，获取还书记录的分页结果
     *
     * @param baseRequest 基本请求对象
     * @return 还书记录的分页结果
     */
    @Override
    public PageInfo<Return> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Return> returns = returnMapper.listByCondition(baseRequest);
        return new PageInfo<>(returns);
    }

    /**
     * 添加还书记录
     *
     * @param returnEntity 待添加还书记录
     */
    @Transactional
    @Override
    public void save(Return returnEntity) {
        returnEntity.setStatus("已归还");
        borrowMapper.updateStatus("已归还", returnEntity.getId());
        returnEntity.setId(null);
        returnEntity.setRealDate(new Timestamp(new Date().getTime()));
        returnMapper.save(returnEntity);
        bookMapper.updateNumByNo(returnEntity.getBookNo());

        Book book = bookMapper.getByNo(returnEntity.getBookNo());
        if (book != null) {
            long until = 0;
            if (returnEntity.getRealDate().toInstant().isBefore(returnEntity.getReturnDate().toInstant())) {
                until = returnEntity.getRealDate().toInstant().until(returnEntity.getReturnDate().toInstant(), ChronoUnit.DAYS);
            } else if (returnEntity.getRealDate().toInstant().isAfter(returnEntity.getReturnDate().toInstant())) {
                until = -returnEntity.getReturnDate().toInstant().until(returnEntity.getRealDate().toInstant(), ChronoUnit.DAYS);
            }
            int score = (int) until * book.getScore();
            User user = userMapper.getByUsername(returnEntity.getUserNo());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if (account < 0) {
                user.setStatus(false);
            }
            userMapper.updateById(user);
        }
    }

    /**
     * 根据ID删除还书记录
     *
     * @param id 还书ID
     */
    @Override
    public void deleteById(Integer id) {
        returnMapper.deleteById(id);
    }
}
