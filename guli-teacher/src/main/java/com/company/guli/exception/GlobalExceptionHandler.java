package com.company.guli.exception;

import com.company.guli.result.AppResponse;
import com.company.guli.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //1.全局异常类型
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AppResponse error(Exception e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return AppResponse.fail("出错了");
    }

//    @ExceptionHandler(EduException.class)
//    @ResponseBody
//    public AppResponse error(EduException e) {
//        e.printStackTrace();
//        log.error(ExceptionUtil.getMessage(e));
//        return AppResponse.fail(e.getMsg());
//    }
}