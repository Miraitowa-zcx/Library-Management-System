package com.example.springboot.mapper;

import com.example.springboot.mapper.po.BorrowReturnCountPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图表映射器接口
 *
 * @author 20383
 */
@Mapper
public interface ChartsMapper {

    /**
     * 根据时间范围和类型获取借还数量
     *
     * @param timeRange 时间范围
     * @param type      类型
     * @return 借还数量
     */
    List<BorrowReturnCountPo> getCountByTimeRange(@Param("timeRange") String timeRange, @Param("type") int type);
}
