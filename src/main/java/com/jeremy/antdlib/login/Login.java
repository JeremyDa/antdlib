package com.jeremy.antdlib.login;

import com.jeremy.core.exception.ExceptionCenter;
import com.jeremy.core.service.FirstService;
import com.jeremy.core.util.TokenUtil;
import com.jeremy.menu.MenuService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private MenuService menuService;

  @Autowired
  private UserService userService;

  @Autowired
  private FirstService firstService;


  @RequestMapping(value = {"/currentUser","/api/currentUser"})
  public Object currentUser(@RequestBody Map<String, Object>paramMap) throws Exception {

    if(paramMap.get("account") == null) {
      throw new ExceptionCenter("500","参数有误");
    }
    List<Map> noticeList = firstService.selectList("notice","selectByPrimaryKey",null,"account",paramMap.get("account"),"total",true);

    List<Map> list = (List) firstService.excuteSqlCheck("user","selectByPrimaryKey",1,"请重新登录",null,"account", (String) paramMap.get("account"));

    Map userMap = list.get(0);
    userMap.put("unreadCount",noticeList.size());
    return userMap;

  }

  @RequestMapping(value = "/login.account")
  public Object login(@RequestBody Map<String, Object> paramMap) throws Exception {

    logger.debug(paramMap.toString());

    // 定义数据结构
    Map retMap = new HashMap();
    Map logMap = new HashMap();

    List<Map> list = (List) firstService.selectList("user","login",
        "account", (String) paramMap.get("account"),
        "password",(String) paramMap.get("password")
    );
    if(list.size() == 1){



      int roleid = (int) list.get(0).get("roleid");

      retMap.put("account",paramMap.get("account"));
      retMap.put("returnCode","0000");
      retMap.put("roleid",roleid);
      retMap.put("status","ok");
      retMap.put("token", TokenUtil.getToken((String)paramMap.get("account"),(String)paramMap.get("password")));
      retMap.put("menu",menuService.gemenuByRoleid(roleid,

          // v2
          "routes"

          // v4
//          "children"
          ));
    }else {
      throw new ExceptionCenter(HttpStatus.INTERNAL_SERVER_ERROR,"用户名或密码错误");
    }

    return retMap;
  }

  @RequestMapping(value = "/api/login.user.modifyPassword")
  public Object modifyPassword(@RequestBody Map<String, Object> paramMap) throws Exception {

    logger.debug(paramMap.toString());

    // 定义数据结构
    Map retMap = new HashMap();
    Map logMap = new HashMap();

    userService.modifyPassword(paramMap,retMap,logMap);

    return retMap;
  }

}
