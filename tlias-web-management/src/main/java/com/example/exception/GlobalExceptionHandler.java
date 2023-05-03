package com.example.exception;

import com.example.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//可以用于全局处理控制器里的异常。
@RestControllerAdvice
public class GlobalExceptionHandler {

//    用于指定异常处理方法。当与@RestControllerAdvice配合使用时，用于全局处理控制器里的异常。
//    有异常发生使，输出栈信息，并返回失败信息。
    @ExceptionHandler
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error("操作失败，请联系管理员");
    }
}
