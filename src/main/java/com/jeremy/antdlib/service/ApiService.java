package com.jeremy.antdlib.service;

import com.jeremy.core.exception.ExceptionCenter;
import com.jeremy.core.service.FirstService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  //业务逻辑
public class ApiService {

  @Autowired
  private FirstService firstService;

  public void example(String namespace, String sqlid, Map paramMap) throws ExceptionCenter {
    int ret = 0;

    //增删改
    ret = firstService.update(namespace,sqlid);

    ret = firstService.update(namespace,sqlid,paramMap);

    ret = firstService.update(namespace,sqlid,paramMap,"id",123, "level", "A");


    // 查询一个字符串／数字
    Object obj = null;
    obj = firstService.selectOne(namespace,sqlid);

    obj = firstService.selectOne(namespace,sqlid,"id",123);

    obj = firstService.selectOne(namespace,sqlid, paramMap, "id",123, "age",12);

    // 查询list,查所有，不带分页
    List<Map<String,Object>> list = null;
    list = firstService.selectList(namespace,sqlid);

    list = firstService.selectList(namespace,sqlid,"id",123);

    list = firstService.selectList(namespace,sqlid, paramMap, "id",123, "age",12, "level", "A");


    // 通用的执行sql ，
    // 入参
//    currentPage: 1
//    pageSize: 20

    obj = firstService.excuteSql(namespace,sqlid, paramMap, "id",123);

    // 返回
//    current: 1
//    total: 2
//    totalPage: 1
//    pageSize: 20

    // 通用的执行sql，如果查询结果的list长度不等于1，抛错提示报错信息
    obj = firstService.excuteSqlCheck(namespace,sqlid, 1,"报错信息",paramMap, "id",123);

    return ;
  }

}
