package com.littlejie.demoutil;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by littlejie on 16/9/10.
 */
public class Util {

    public static void startActivity(Context context, Class clz) {
        context.startActivity(new Intent(context, clz));
    }

    public static List<String> generateString(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add("item " + i);
        }
        return list;
    }
}

