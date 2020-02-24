package com.jeremy.antdlib.notify;

import com.jeremy.core.exception.ExceptionCenter;
import com.jeremy.core.service.FirstService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Notify {

  @Autowired
  private FirstService firstService;

  @RequestMapping(value = {"/api/notices"})
  public Object  notices(@RequestBody Map paramMap) throws ExceptionCenter {
//    lastItemId: "000000012"
//    type: "notification"
//    offset: 5
    return firstService.selectList("notice","selectByPrimaryKey",paramMap);

  }
}
