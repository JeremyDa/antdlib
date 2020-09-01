package com.jeremy.antdlib.login;

import com.auth0.jwt.JWT;
import com.jeremy.core.exception.ExceptionCenter;
import com.jeremy.core.service.FirstService;
import com.jeremy.core.util.ParamUtil;
import com.jeremy.core.util.TokenUtil;
import com.jeremy.menu.MenuService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional(rollbackFor={ExceptionCenter.class, RuntimeException.class})
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

  @RequestMapping(value = "/version.newest")
  public Object newest(@RequestBody Map<String, Object> paramMap) {
    return ParamUtil.arrayToMap(null,"newest",firstService.selectOne("version","newest"));
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
      int userid = (int) list.get(0).get("id");
      int roleid = 0;
      if(list.get(0).get("roleid") == null){
        List<Map> roleidList = firstService.selectList("roleuser","selectByPrimaryKey","userid",userid);
        roleid = (int) roleidList.get(0).get("roleid");
      }else{
        roleid = (int) list.get(0).get("roleid");
      }

      retMap.put("account",paramMap.get("account"));
      retMap.put("userid",userid);
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

  @RequestMapping(value = "/api/getMenu")
  public Object getMenu(@RequestBody Map<String, Object> paramMap) throws Exception {

    logger.debug(paramMap.toString());

    // 定义数据结构
    Map retMap = new HashMap();
    Map logMap = new HashMap();

    List<Map> list = (List) firstService.selectList("user","selectRoleidByProjectid", paramMap);
    if(list.size() == 1){

      int roleid = (int) list.get(0).get("roleid");

      retMap.put("account",paramMap.get("account"));
      retMap.put("returnCode","0000");
      retMap.put("roleid",roleid);
      retMap.put("status","ok");
      retMap.put("menu",menuService.gemenuByRoleid(roleid,

          // v2
          "routes"

          // v4
//          "children"
      ));
    }else {
      throw new ExceptionCenter(HttpStatus.INTERNAL_SERVER_ERROR,"查询菜单异常");
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

  @RequestMapping(value = "/api/user.batch.insertSelective")
  public Object batchInsert(@RequestBody Map<String, Object> paramMap) throws Exception {

    logger.debug(paramMap.toString());

    // 定义数据结构
    Map retMap = new HashMap();

    firstService.update("user","insertSelective",paramMap);
    System.out.println("paramMap.get(\"id\"):"+paramMap.get("id"));
    paramMap.put("userid",paramMap.get("id"));
    paramMap.remove("id");

    firstService.update("roleuser","deleteByPrimaryKey","userid",paramMap.get("userid"));

    firstService.update("roleuser","insertBatch",paramMap);

    retMap.put("success",true);
    return retMap;
  }

  @RequestMapping(value = "/api/user.batch.updateByPrimaryKeySelective")
  public Object batchUpdate(@RequestBody Map<String, Object> paramMap) throws Exception {

    logger.debug(paramMap.toString());

    // 定义数据结构
    Map retMap = new HashMap();
    firstService.update("user","updateByPrimaryKeySelective",paramMap);

    firstService.update("roleuser","deleteByPrimaryKey","userid",paramMap.get("id"));
    paramMap.put("userid",paramMap.get("id"));
    paramMap.remove("id");

    firstService.update("roleuser","insertBatch",paramMap);

    retMap.put("success",true);
    return retMap;
  }

  @RequestMapping(value = "/api/user.batch.deleteByPrimaryKey")
  public Object batchDelete(@RequestBody Map<String, Object> paramMap) throws Exception {

    logger.debug(paramMap.toString());

    // 定义数据结构
    Map retMap = new HashMap();
    firstService.update("user","deleteByPrimaryKey",paramMap);

    firstService.update("roleuser","deleteByPrimaryKey","userid",paramMap.get("id"));

    retMap.put("success",true);
    return retMap;
  }

  @RequestMapping(value = "/api/getAccount")
  public Object getAccount(@RequestBody Map<String, Object> paramMap) throws Exception {
    Map retMap = new HashMap();
    if(paramMap.get("token") == null){
      return new HashMap<>();
    }
    String account = JWT.decode((String) paramMap.get("token")).getAudience().get(0);
    List<Map> list = (List) firstService.selectList("user","selectByPrimaryKey",
        "account", account
    );
    if(list.size() == 1){
      int userid = (int) list.get(0).get("id");
      int roleid = 0;
      if(list.get(0).get("roleid") == null){
        List<Map> roleidList = firstService.selectList("roleuser","selectByPrimaryKey","userid",userid);
        roleid = (int) roleidList.get(0).get("roleid");
      }else{
        roleid = (int) list.get(0).get("roleid");
      }

      retMap.put("account",account);
      retMap.put("userid",userid);
      retMap.put("returnCode","0000");
      retMap.put("roleid",roleid);
      retMap.put("status","ok");
      retMap.put("menu",menuService.gemenuByRoleid(roleid,

          // v2
          "routes"

          // v4
//          "children"
      ));
    }else {
      throw new ExceptionCenter(HttpStatus.INTERNAL_SERVER_ERROR,"");
    }

    return retMap;
  }

}
