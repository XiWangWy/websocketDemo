package com.example.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxi on 2018/12/18.
 */
@Service
public class AddPersonImpl implements AddPersonService{

    private Map<String,JSONObject> allPeople = new HashMap<>();

    @Override
    public void addPerson(String uuid) {
        if (allPeople.get(uuid) == null){
            allPeople.put(uuid,new JSONObject());
        }else {
            JSONObject object = allPeople.get(uuid);
            object.put("uuid",uuid);
            allPeople.put(uuid,object);
        }

    }

    @Override
    public int size() {
        return allPeople.size();
    }

    @Override
    public JSONObject getPerson(String uuid) {
        return allPeople.get(uuid);
    }

    @Override
    public Map<String, JSONObject> getAllPeople() {
        return allPeople;
    }
}
