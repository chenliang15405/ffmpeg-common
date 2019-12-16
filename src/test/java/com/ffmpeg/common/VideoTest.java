package com.ffmpeg.common;

import com.ffmpeg.common.response.Result;
import com.ffmpeg.common.video.VideoOperation;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @auther alan.chen
 * @time 2019/9/11 3:11 PM
 */
public class VideoTest {

    private static final String ffmpegEXE = "/Users/alan.chen/Documents/notes/ffmpeg";

    @Test
    public void testConverTest() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/2222.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/1/convert.flv";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.videoConvert(inputPath, outPutPath);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void videoRotateTest() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/2222.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/1/rotate.mp4";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.videoRotate(inputPath,2,"","" , outPutPath);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void videoConverToGifTest() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/11111.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/1/out.gif";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.videoConvertToGif(inputPath,outPutPath, true);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void convertorWithBgmTest() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/11111.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/1/222.mp4";
        String noSoundPath = "/Users/alan.chen/Documents/notes/test/1/nosound.mp4";
        String audioPath = "/Users/alan.chen/Documents/notes/young.mp3";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.convertorWithBgmNoOriginCommon(inputPath, outPutPath, noSoundPath, audioPath, 5);
        Integer expected = 0;
        Assert.assertEquals(expected, result.getCode());
        System.out.println(result.getErrMessage());
    }

}
