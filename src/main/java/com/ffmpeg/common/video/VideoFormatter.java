package com.ffmpeg.common.video;

import com.ffmpeg.common.FFMpegExceptionn;
import com.ffmpeg.common.utils.BaseFileUtil;

import java.io.*;
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

    public static File createTempVideoFile(String dir) throws IOException {
        File temp = File.createTempFile("video_temp_",".txt");
        if(temp.exists()) {
            writeVideoList(dir, temp);
        }
        return temp;
    }

    public static void writeVideoList(String sourcePath, File file) {
        File[] files = BaseFileUtil.listFiles(sourcePath);
        try(FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw)) {


            for (File item : files) {
                if(!item.isHidden()) {
                    bw.write("file " + item.getAbsolutePath());
                    bw.newLine();
                }
            }
            bw.flush();
        } catch (IOException e) {
            throw new FFMpegExceptionn(e);
        }
    }

}
