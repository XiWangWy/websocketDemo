package com.example.websocket;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by wangxi on 2018/12/18.
 */
public interface AddPersonService {
    void addPerson(String uuid);
    int size();
    JSONObject getPerson(String uuid);
    Map<String,JSONObject> getAllPeople();
}
