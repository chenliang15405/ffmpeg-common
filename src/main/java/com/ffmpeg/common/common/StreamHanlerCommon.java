package com.ffmpeg.common.common;

import com.ffmpeg.common.response.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther alan.chen
 * @time 2019/9/17 5:22 PM
 */
public class StreamHanlerCommon {

    /**
     * 释放流
     */
    public static Result closeStreamQuietly(Process proc) throws IOException {
        Result result = new Result();
        StringBuffer output = new StringBuffer();
        BufferedReader stdout = null;
        try {
            stdout = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line;
            while ((line = stdout.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
            int code = proc.waitFor();
            result.setCode(code);
            result.setErrMessage(output.toString().trim());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(stdout != null) {
               stdout.close();
            }
        }
        return result;
    }
}
