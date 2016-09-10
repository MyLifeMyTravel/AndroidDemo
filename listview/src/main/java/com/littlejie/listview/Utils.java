package com.littlejie.listview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by littlejie on 16/9/9.
 */
public class Utils {

    public static List<String> generateString(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add("item " + i);
        }
        return list;
    }
}
