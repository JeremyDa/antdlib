package com.jeremy.antdlib.login;

import com.jeremy.core.exception.ExceptionCenter;
import com.jeremy.core.service.FirstService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={ExceptionCenter.class, RuntimeException.class})
public class UserService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private FirstService firstService;

  public void modifyPassword(Map<String, Object> paramMap,
      Map retMap, Map<String, Object> logMap) throws Exception {
    Map param = new HashMap();
    param.put("account", paramMap.get("account"));
    param.put("password", paramMap.get("oldpassword"));
    int count = (int) firstService.selectOne("user","checkPassword", param);
    if (count < 1) {
      throw new ExceptionCenter(HttpStatus.INTERNAL_SERVER_ERROR,"用户名或密码不正确");
    }else if(count > 1){
      throw new ExceptionCenter(HttpStatus.INTERNAL_SERVER_ERROR,"用户数据有误");
    }else if(count == 1){
      int ret = (int) firstService.update("user","updateByPrimaryKeySelective","id",paramMap.get("id"),"account",(String) paramMap.get("account"),"password",(String) paramMap.get("password"));

      if( ret != 1){
        throw new ExceptionCenter(HttpStatus.INTERNAL_SERVER_ERROR,"更新密码失败");
      }
    }

  }
}
