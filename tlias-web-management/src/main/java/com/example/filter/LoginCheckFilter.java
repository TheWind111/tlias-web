package com.example.filter;

import com.alibaba.fastjson2.JSONObject;
import com.example.pojo.Result;

import com.example.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//此处filter 和interceptor选一个使用
//过滤所有url
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        获取http连接，根据请求判断是否放行。
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String url = httpServletRequest.getRequestURL().toString();
        log.info("请求的url{}", url);

//        url为登录请求时，放行，执行登录判断
        if (url.contains("login")) {
            log.info("登录操作");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

//        url非登录请求，判断是否可以获取token令牌
        String jwt = httpServletRequest.getHeader("token");
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头为空");
            Result error = Result.error("NOT_LOGIN");
            //文档要求需要写入json格式字符串
            String notLogin = JSONObject.toJSONString(error);
            servletResponse.getWriter().write(notLogin);
            return;
        }
//       解析token，判断是否为合法用户
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("令牌解析失败");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            servletResponse.getWriter().write(notLogin);
            return;
        }
//        合法用户，放行操作。
        log.info("令牌合法");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
