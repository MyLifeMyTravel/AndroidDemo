package com.littlejie.utils;

import android.os.Environment;

/**
 * Created by Lion on 2016/6/23.
 */
public class Constants {

    /*--------------------文件相关---------------------*/
    public static final String PATH_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATH_LITTLE = PATH_ROOT + "/littlejie";
    public static final String PATH_CRASH_FOLDER = PATH_LITTLE + "/crash";
    public static final String PATH_CACHE_FOLDER = PATH_LITTLE + "/cache";

    public static final String PARAM_INDEX = "index";
    public static final String PARAM_IMAGE_INFO_LIST = "image_info_list";
    public static final String PARAM_SELECT_IMAGE_INFO = "select_image_info";
}
