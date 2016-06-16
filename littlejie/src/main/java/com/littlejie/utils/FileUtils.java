package com.littlejie.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by yangchenwei on 2015/4/2.
 */
public class FileUtils {

    /**
     * SIZE_MB
     */
    public static final long SIZE_MB = 1024L * 1024L;

    private FileUtils() {
    }

    public static void writeToFile(String path, String content) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(content.getBytes("utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}