package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.service.IChartsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 图表控制器
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/charts")
public class ChartsController {

    private final IChartsService chartsService;

    /**
     * 构造函数
     *
     * @param chartsService 图表服务
     */
    @Autowired
    public ChartsController(IChartsService chartsService) {
        this.chartsService = chartsService;
    }

    /**
     * 查询图表
     *
     * @param timeRange 时间范围
     * @return 图表结果
     */
    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange) {
        return Result.success(chartsService.getCountByTimeRange(timeRange));
    }
}
