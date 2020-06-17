package com.ffmpeg.common.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @auther alan.chen
 * @time 2019/9/11 3:07 PM
 */
public class BaseFileUtil {

    private static final String VIDEO_SUFFIX_MPG = "mpg";
    private static final String VIDEO_SUFFIX_MPEG = "mpeg";

    public static void checkAndMkdir(String path) {
        File file = new File(path);
        if(file.getParentFile() != null || !file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
    }

    public static boolean hashFile(String path) {
        File file = new File(path);
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            return files != null && files.length > 0;
        }
        return false;
    }

    public static File[] listFiles(String path) {
        File file = new File(path);
        return file.listFiles();
    }

    public static String[] list(String path) {
        File file = new File(path);
        return file.list();
    }

    public static boolean unifySuffix(File[] files) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isHidden()) {
                continue;
            }
            String filename = files[i].getName();
            String suffix = filename.substring(filename.lastIndexOf("."));
            set.add(suffix);
        }
        return set.size() == 1;
    }

    public static boolean isMpgOrMpeg(File[] files) {
        String file = files[0].getName();
        if(VIDEO_SUFFIX_MPG.endsWith(file) || VIDEO_SUFFIX_MPEG.endsWith(file)) {
            return true;
        }
        return false;
    }

}
