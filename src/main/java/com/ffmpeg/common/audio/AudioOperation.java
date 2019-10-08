package com.ffmpeg.common.audio;

import com.ffmpeg.common.FFMpegExceptionn;
import com.ffmpeg.common.common.StreamHanlerCommon;
import com.ffmpeg.common.response.Result;
import com.ffmpeg.common.utils.BaseFileUtil;
import com.ffmpeg.common.utils.StrUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther alan.chen
 * @time 2019/9/17 3:51 PM
 */
public class AudioOperation {


    /**
     *  ffmpeg文件路径
     */
    private String ffmpegEXE;

    public AudioOperation(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public static AudioOperation builder(String ffmpegEXE) {
        return new AudioOperation(ffmpegEXE);
    }



    /**
     * 将多个音频文件拼接为一个音频文件并输出
     *
     * @param bgmOutPath 输出音频文件
     * @param bgmInputPath 输入的音频文件
     * @return
     */
    public Result audioConcat(String bgmOutPath, String... bgmInputPath) {
        if(StrUtils.checkBlank(bgmOutPath) || bgmInputPath.length <= 0) {
            throw new FFMpegExceptionn("请输入正确的音频输入和输出路径");
        }
        BaseFileUtil.checkAndMkdir(bgmOutPath);
        try {
            List<String> bgmList = Arrays.asList(bgmInputPath);

            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-f");
            commands.add("-concat");

            bgmList.forEach(item -> {
                commands.add("-i");
                commands.add(item);
            });

            commands.add("-c");
            commands.add("copy");
            commands.add(bgmOutPath);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 通过指定开始时间和结束时间 裁剪音频
     *
     * @param bgmInputPath 音频输入绝对路径
     * @param bgmOutPath 音频输出绝对路径
     * @param startTime 开始时间
     * @param endTime 截取秒数
     * @return
     */
    public Result audioCut(String bgmInputPath, String bgmOutPath, String startTime, String endTime) {
//         ffmpeg -i out.mp3 -ss 00:00:00 -t 00:06:38 -acodec copy love3.mp3
        String str = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(startTime);
        Matcher matcher1 = pattern.matcher(endTime);
        if(!matcher.matches() || !matcher1.matches()) {
            throw new FFMpegExceptionn("输入的时间格式错误");
        }
        try {
            BaseFileUtil.checkAndMkdir(bgmOutPath);

            Stream<String> stream = Stream.of(ffmpegEXE, "-i", bgmInputPath, "-ss", startTime, "-t",
                    endTime, "-acodec", "copy", bgmOutPath);

            List<String> commands = stream.collect(Collectors.toList());

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }


    /**
     * 从视频中提取音频
     *
     * @param inputVideo 视频绝对路径
     * @param outAudio 输出音频绝对路径
     * @return
     */
    public Result getBgmFromVideo(String inputVideo, String outAudio) {
        //ffmpeg -y -i source.mp4 -vn output.wav
        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outAudio)) {
            throw new FFMpegExceptionn("请输入正确的路径");
        }
        BaseFileUtil.checkAndMkdir(outAudio);
        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-vn");
            commands.add(outAudio);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }


}
