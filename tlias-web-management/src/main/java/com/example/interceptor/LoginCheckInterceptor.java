package com.example.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    //拦截前 获取url,判断是否拥有合法token,为登录合法用户。
//    是则返回true,否则 false，拦截
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
        System.out.println("preHandler");

        String url = httpServletRequest.getRequestURL().toString();
        log.info("请求的url{}", url);

        String jwt = httpServletRequest.getHeader("token");
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头为空");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            servletResponse.getWriter().write(notLogin);
            return false;
        }

        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("令牌解析失败");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            servletResponse.getWriter().write(notLogin);
            return false;
        }

        log.info("令牌合法");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
