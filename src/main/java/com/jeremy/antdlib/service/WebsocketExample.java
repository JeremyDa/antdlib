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

    // messageType: 1-上线，2-下线，3-在线名单，4-消息
    // from: 发送方
    // to: 接收方，all-广播消息

    // 广播发送
    boolean ok = webSocket.sendMessageAll(
        JSON.toJSONString(ParamUtil.arrayToMap(null, "messageType", 1, "from", "account","to","all")));

    // ok:true-发送成功，false-发送失败
    System.out.println("ok:"+ok);

    // 定向发送
    ok = webSocket.sendMessageTo(JSON.toJSONString(
        ParamUtil.arrayToMap(null, "messageType", 4, "message", "message", "from", "account","to","admin")),
        "admin");

  }
}
