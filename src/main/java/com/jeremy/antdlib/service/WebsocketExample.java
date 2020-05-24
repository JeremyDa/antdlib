package com.jeremy.antdlib.service;

import com.alibaba.fastjson.JSON;
import com.jeremy.core.util.ParamUtil;
import com.jeremy.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebsocketExample {

  @Autowired
  private WebSocket webSocket;

  public void example(){

    // from: 发送方
    // to: 接收方

    // 广播发送
    boolean ok = webSocket.sendMessageAll(
        JSON.toJSONString(ParamUtil.arrayToMap(null, "title", "title", "body", "body", "from", "account","to","all")));

    // ok:true-发送成功，false-发送失败
    System.out.println("ok:"+ok);

    // 定向发送
    ok = webSocket.sendMessageTo(JSON.toJSONString(
        ParamUtil.arrayToMap(null, "title", "title", "body", "body", "from", "account","to","admin")),
        "admin");

    System.out.println("ok:"+ok);

  }
}
