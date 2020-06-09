package com.ffmpeg.common.video;

import java.util.List;

/**
 * @author alan.chen
 * @date 2020/6/9 5:29 PM
 */
public class VideoFormatter {


    /**
     * 文件名称集合使用"|"分隔
     *
     * @param fileNameList
     * @return
     */
    public static String fileNameFormat(List<String> fileNameList) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < fileNameList.size(); i++) {
            if(i != fileNameList.size() - 1) {
                strBuilder.append(fileNameList.get(i)).append("|");
            } else {
                strBuilder.append(fileNameList.get(i));
            }
        }
        return strBuilder.toString();
    }

}
