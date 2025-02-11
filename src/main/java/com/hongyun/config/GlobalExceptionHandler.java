package com.hongyun.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObjectVO<String> handleException(Exception e) {
        log.error("An error occurred: {}", e.getMessage());
        e.printStackTrace();
        ResponseObjectVO<String> responseObjectVO = new ResponseObjectVO<>();
        return responseObjectVO.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
    }
}
