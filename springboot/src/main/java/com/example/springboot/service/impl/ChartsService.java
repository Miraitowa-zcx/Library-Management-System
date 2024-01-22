package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.mapper.ChartsMapper;
import com.example.springboot.mapper.po.BorrowReturnCountPo;
import com.example.springboot.service.IChartsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 图表服务实现类，实现IChartsService接口
 *
 * @author 20383
 */
@Slf4j
@Service
public class ChartsService implements IChartsService {

    private final ChartsMapper chartsMapper;

    /**
     * 构造方法，注入图标mapper
     *
     * @param chartsMapper 图标Mapper实例
     */
    @Autowired
    public ChartsService(ChartsMapper chartsMapper) {
        this.chartsMapper = chartsMapper;
    }

    /**
     * 查询指定时间范围内的图表数据
     *
     * @param timeRange 时间范围
     * @return 图表数据Map对象
     */
    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange) {
            case "week":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -29), today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -89), today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateStrRange = dateTimeToDateStr(dateRange);
        map.put("date", dateStrRange);
        List<BorrowReturnCountPo> borrowCount = chartsMapper.getCountByTimeRange(timeRange, 1);
        List<BorrowReturnCountPo> returnCount = chartsMapper.getCountByTimeRange(timeRange, 2);
        map.put("borrow", countList(borrowCount, dateStrRange));
        map.put("return", countList(returnCount, dateStrRange));
        return map;
    }

    /**
     * 将List<DateTime>对象转换为List<String>对象
     *
     * @param dateTimeList DateTime对象列表
     * @return String对象列表
     */
    private List<String> dateTimeToDateStr(List<DateTime> dateTimeList) {
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for (DateTime dateTime : dateTimeList) {
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    /**
     * 将List<BorrowReturnCountPo>对象转换为List<Integer>对象
     *
     * @param countPoList BorrowReturnCountPo对象列表
     * @param dateRange   String对象列表
     * @return Integer对象列表
     */
    private List<Integer> countList(List<BorrowReturnCountPo> countPoList, List<String> dateRange) {
        List<Integer> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPoList)) {
            return list;
        }
        for (String date : dateRange) {
            Integer count = countPoList.stream().filter(countPo -> date.equals(countPo.getDate()))
                    .map(BorrowReturnCountPo::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }
}
