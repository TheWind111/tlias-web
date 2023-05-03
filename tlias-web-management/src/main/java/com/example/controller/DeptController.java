package com.example.controller;

import com.example.anno.Log;
import com.example.pojo.Dept;
import com.example.pojo.Result;
import com.example.service.impl.DeptServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    DeptServiceImpl deptService;

    //    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping()
    public Result list() {
        log.info("查询用户信息");
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    @Log
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        log.info("删除部门信息:{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

//    注解了Log的方法 会插入一条操作日志。 见LogAspect
    @Log
    @PostMapping()
    public Result add(@RequestBody Dept dept){
        log.info("添加部门:{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id){
        log.info("根据id查询,id:{}",id);
        Dept dept=deptService.getById(id);
        return Result.success(dept);
    }

    @PutMapping()
    public Result modify(@RequestBody Dept dept){
        log.info("更新部门数据");
        deptService.modify(dept);
        return Result.success();
    }

}
