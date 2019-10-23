package com.ffmpeg.common;

import com.ffmpeg.common.audio.AudioOperation;
import com.ffmpeg.common.response.Result;
import com.ffmpeg.common.video.VideoOperation;
import org.junit.Test;

/**
 * @auther alan.chen
 * @time 2019/9/17 5:40 PM
 */
public class AudioTest {

    private static final String ffmpegEXE = "/Users/alan.chen/Documents/notes/ffmpeg";

    @Test
    public void audioScaleTest() {
        String inputPath = "/Users/alan.chen/Documents/notes/VideoTest/2222.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/VideoTest/1/out.mp4";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.videoScale(inputPath,"1080","1920", outPutPath);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void audioFormatTest() {
        String inputPath = "/Users/alan.chen/Documents/notes/VideoTest/amr.amr";
        String outPutPath = "/Users/alan.chen/Documents/notes/VideoTest/mp3.mp3";
        AudioOperation ffmpeg = AudioOperation.builder(ffmpegEXE);
        Result result = ffmpeg.transFormatToMp3Audio(inputPath, outPutPath);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }
}
