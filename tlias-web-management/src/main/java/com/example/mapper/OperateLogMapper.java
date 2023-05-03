package com.example.mapper;

import com.example.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

//数据库插入一条信息：操作人，操作时间，操作类....等，
@Mapper
public interface OperateLogMapper {

    @Insert("insert into operate_log (operate_user, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateUser},#{operateTime},#{className},#{methodName},#{methodParams},#{returnValue},#{costTime});")
    public void insert(OperateLog log);
}
