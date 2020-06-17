package com.ffmpeg.common.common;


import com.ffmpeg.common.FFMpegExceptionn;
import com.ffmpeg.common.response.Result;

import java.io.IOException;
import java.util.List;

/**
 * @author alan.chen
 * @date 2020/6/7 9:07 PM
 */
public class ProcessCommand {

    /**
     * 执行命令
     *
     * @param command 命令参数集合
     * @return 执行结果信息对象
     */
    public static Result start(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e);
        }
    }

}
