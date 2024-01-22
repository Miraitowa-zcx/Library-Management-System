package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Return;
import com.example.springboot.mapper.po.BorrowReturnCountPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 还书映射器接口
 *
 * @author 20383
 */
@Mapper
public interface ReturnMapper {

    /**
     * 根据条件查询还书列表
     *
     * @param baseRequest 条件
     * @return 还书列表
     */
    List<Return> listByCondition(BaseRequest baseRequest);

    /**
     * 添加还书记录
     *
     * @param returnEntity 还书记录
     */
    void save(Return returnEntity);

    /**
     * 根据ID删除还书记录
     *
     * @param id ID
     */
    void deleteById(Integer id);
}
