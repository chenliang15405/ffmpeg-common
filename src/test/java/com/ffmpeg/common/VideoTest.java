package com.ffmpeg.common;

import com.ffmpeg.common.response.Result;
import com.ffmpeg.common.video.VideoOperation;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther alan.chen
 * @time 2019/9/11 3:11 PM
 */
public class VideoTest {

    private static final String ffmpegEXE = "/Users/alan.chen/Documents/notes/ffmpeg";

    public static final Integer EXPECTED = 0;

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
    public void testConverTest1() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/convert.flv";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/001f.mpg";
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
        Assert.assertEquals(EXPECTED, result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void transformVideoCoverTest() {
        String inputPath = "/Users/alan.chen/Documents/notes/test/11111.mp4";
        String imagePath = "/Users/alan.chen/Documents/notes/test/mail.jpg";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/2/111_bg.mp4";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.transformVideoCover(inputPath, imagePath, outPutPath);
        Assert.assertEquals(EXPECTED, result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void mergeMultiOnlineVideosTest() {
        String inputPath = "/Users/alan.chen/Documents/notes/test/video.txt";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/mutionout.mp4";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.mergeMultiOnlineVideos(new File(inputPath), outPutPath);
        Assert.assertEquals(EXPECTED, result.getCode());
        System.out.println(result.getErrMessage());
        System.out.println(result);
    }

    @Test
    public void mergeMultiVideosOfTsFormatTest() {
        List<String> list = new ArrayList<>();
        list.add("/Users/alan.chen/Documents/notes/test/2/1509761497_168.ts");
        list.add("/Users/alan.chen/Documents/notes/test/2/1509761556_175.ts");
        list.add("/Users/alan.chen/Documents/notes/test/2/1509761539_173.ts");
        String outPutPath = "/Users/alan.chen/Documents/notes/test/mergeMultiVideosOfTsFormat.mp4";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.mergeMultiVideosOfTsFormat(list, outPutPath);
        Assert.assertEquals(EXPECTED, result.getCode());
        System.out.println(result.getErrMessage());
        System.out.println(result);
    }

    @Test
    public void mergeMultiVideosByFileTest() {
        String inputPath = "/Users/alan.chen/Documents/notes/test/video-example.txt";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/mergeVideo.mp4";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.mergeMultiVideosByFile(new File(inputPath), outPutPath);
        Assert.assertEquals(EXPECTED, result.getCode());
    }

}
