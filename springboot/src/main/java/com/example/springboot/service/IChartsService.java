package com.example.springboot.service;

import java.util.Map;

/**
 * 图表服务接口
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
public interface IChartsService {

    /**
     * 查询图表
     *
     * @param timeRange 时间范围
     * @return 图表数据
     */
    Map<String, Object> getCountByTimeRange(String timeRange);
}
