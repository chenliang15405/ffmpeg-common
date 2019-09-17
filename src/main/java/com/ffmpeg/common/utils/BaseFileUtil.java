package com.ffmpeg.common.utils;

import java.io.File;

/**
 * @auther alan.chen
 * @time 2019/9/11 3:07 PM
 */
public class BaseFileUtil {


    public static void checkAndMkdir(String path) {
        File file = new File(path);
        if(file.getParentFile() != null || !file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
    }

}
