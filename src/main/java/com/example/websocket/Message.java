package com.example.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created by wangxi on 2018/12/14.
 */
@Data
public class Message {
    private String sendMessage;
    private JSONObject extendObj;
}
