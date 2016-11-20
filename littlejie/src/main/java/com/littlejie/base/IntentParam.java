package com.littlejie.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by littlejie on 2016/11/19.
 */

public class IntentParam {

    Map<String, Object> map = new HashMap<>();

    public IntentParam() {
    }

    public IntentParam(Map<String, String> m) {
        Set<String> setKey = m.keySet();
        for (String key : setKey) {
            map.put(key, m.get(key));
        }
    }

    public IntentParam add(String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
        return this;
    }

    public Object get(String key) {
        return map.get(key);
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
