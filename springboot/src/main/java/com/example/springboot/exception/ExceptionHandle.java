package com.example.springboot.exception;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 *
 * @author 20383
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    /**
     * 处理ServiceException异常
     *
     * @param e ServiceException异常对象
     * @return 返回处理结果
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result serviceExceptionError(ServiceException e) {
        log.error("业务异常", e);
        String code = e.getCode();
        if (StrUtil.isNotBlank(code)) {
            return Result.error(code, e.getMessage());
        }
        return Result.error(e.getMessage());
    }

    /**
     * 处理Exception异常
     *
     * @param e Exception异常对象
     * @return 返回处理结果
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandle(Exception e) {
        log.error("系统异常", e);
        return Result.error("系统异常");
    }
}
