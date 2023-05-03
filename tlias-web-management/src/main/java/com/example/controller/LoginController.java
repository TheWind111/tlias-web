package com.example.controller;


import com.example.pojo.Emp;
import com.example.pojo.Result;
import com.example.service.impl.EmpServiceImpl;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpServiceImpl empService;

//    jwt令牌 控制登录
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("用户登录 " + emp.getUsername() + " " + emp.getPassword());
        Emp e = empService.login(emp);
//        存在该用户信息，登录成功，生成jwt令牌信息，返回
        if (e != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            claims.put("name", e.getName());
            return Result.success(JwtUtils.generatorJwt(claims));
        }
        return Result.error("用户信息不正确");
    }
}
