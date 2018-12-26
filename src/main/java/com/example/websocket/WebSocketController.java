package com.example.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * Created by wangxi on 2018/12/14.
 */

@Slf4j
@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private AddPersonImpl addPersonImp;

    @MessageMapping("/receive")
    @SendTo("/topic/greetings")
    public Message sendToGroup(Message message){
        log.info("广播消息：=====》"+message);
        return message;
    }

    @MessageMapping("/sendShackCount")
    @SendTo("/topic/sendShackCount")
    public Map<String, JSONObject> sendShackCount(JSONObject message){
        String uuid = message.getString("uuid");
        addPersonImp.getPerson(uuid).putAll(message);
        log.info("广播消息sendShackCount：=====》"+message);
        return addPersonImp.getAllPeople();
    }


    @MessageMapping("/receiveMessage")
    @SendToUser("/topic/toUser")
    public Message sendToUser(Message message){
        log.info("发送小心给：======》" + message);
        return message;
    }

    @MessageMapping("/receiveUserMessage")
    @SendToUser(destinations = "/message",broadcast = false)
    public Message sendToUserMessage(Message message){
        log.info("发送小心给：======》" + message);
        return message;
    }

    @PostMapping("/greetings")
    public void greet(@RequestBody JSONObject greeting) {
        this.simpMessagingTemplate.convertAndSend("/topic/greetings", greeting.toJSONString());
    }

    @PostMapping("/sendToUser")
    public void sendToUser(@RequestBody JSONObject message){
        log.info("单独发消息：======>" + message);
        this.simpMessagingTemplate.convertAndSendToUser(message.getString("id"),"/message",message.toJSONString());
    }


    @PostMapping("/addPerson")
    public JSONObject addPerson(@RequestBody JSONObject object){
        addPersonImp.addPerson(object.getString("uuid"));
        log.info("加入：======>" + object);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("onLine",addPersonImp.size());
        this.simpMessagingTemplate.convertAndSend("/topic/greetings",jsonObject);
        return jsonObject;
    }



    @RequestMapping("/")
    public void test(){

    }
}
