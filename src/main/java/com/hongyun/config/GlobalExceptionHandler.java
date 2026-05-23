package com.hongyun.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseObjectVO<String> handleValidException(ConstraintViolationException e) {
        String msg = e.getMessage().replaceAll(".*\\.", "").replaceFirst(": ", "");
        log.error("参数校验失败：{}", msg);
        return new ResponseObjectVO<String>().getFailResponseVo(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseObjectVO<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("参数校验失败：{}", msg);
        return new ResponseObjectVO<String>().getFailResponseVo(msg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObjectVO<String> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage());
        e.printStackTrace();
        return new ResponseObjectVO<String>().getFailResponseVo(NormalConstants.ERROR_MESSAGE);
    }
}