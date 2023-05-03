package com.example.aop;

import com.alibaba.fastjson2.JSONObject;
import com.example.pojo.OperateLog;
import com.example.mapper.OperateLogMapper;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LogAspect {

    //    记录操作的mapper
    @Autowired
    private OperateLogMapper logMapper;

    //    获取当前请求
    @Autowired
    private HttpServletRequest request;

    //    在调用一个具体方法前和调用后来完成一些具体的任务。
//    此处对注解了log注解的controller方法，在调用前后执行以下操作。
    @Around("@annotation(com.example.anno.Log)")
    public Object recordLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //解析令牌，获取用户id
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJwt(jwt);
        Integer id = (Integer) claims.get("id");

        //获取当前操作时间，利用反射，获取当前的操作类，方法，参数信息
        LocalDateTime operateTime = LocalDateTime.now();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        String methodParams = Arrays.toString(args);

//        获取操作时间，并放行操作
        long begin = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

//        获取返回值
        String returnValue = JSONObject.toJSONString(result);
        Long costTime = end - begin;

//        封装上述信息为OperateLog对象，插入数据库
        OperateLog log = new OperateLog(null, id, operateTime, className, methodName, methodParams, returnValue, costTime);
        logMapper.insert(log);
        return result;
    }
}
