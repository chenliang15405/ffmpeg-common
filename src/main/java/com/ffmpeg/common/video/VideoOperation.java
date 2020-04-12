package com.ffmpeg.common.video;

import com.ffmpeg.common.FFMpegExceptionn;
import com.ffmpeg.common.common.StreamHanlerCommon;
import com.ffmpeg.common.response.Result;
import com.ffmpeg.common.utils.BaseFileUtil;
import com.ffmpeg.common.utils.StrUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @auther alan.chen
 * @time 2019/9/11 11:20 AM
 */
public class VideoOperation {

    /**
     *  ffmpeg文件路径
     */
    private String ffmpegEXE;

    public VideoOperation(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public static VideoOperation builder(String ffmpegEXE) {
        return new VideoOperation(ffmpegEXE);
    }


    /**
     *  视频转换格式
     *
     * @param inputVideo 原始视频绝对路径（带视频名称）
     * @param outputVideo  输出视频绝对路径（带视频名称）
     * @return result 返回执行code和message
     * @throws IOException
     */
    public Result videoConvert(String inputVideo, String outputVideo)  {
        //  ffmpeg -i input.mp4 -y out.mp4
        // ffmpeg -i in.mov -vcodec copy -acodec copy out.mp4  // mov --> mp4
        // ffmpeg -i in.flv -vcodec copy -acodec copy out.mp4
        try {
            if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputVideo)) {
                throw new FFMpegExceptionn("videoInputFullPath or videoOutFullPath must not be null");
            }
            BaseFileUtil.checkAndMkdir(inputVideo);

            List<String> commands = new ArrayList<String>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-vcodec");
            commands.add("copy");
            commands.add("-acodec");

            //commands.add("-y");
            commands.add("copy");
            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands).redirectErrorStream(true);
            Process proc = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(proc);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 保留视频原声合成音频
     *
     * @param bgm 背景音乐路径
     * @param inputVideo 输入音频路径
     * @param outputVideo 输出视频路径
     * @param seconds 输出视频秒数
     * @return
     */
    public Result mergeVideoAndBgmWithOrigin(String bgm, String inputVideo, String outputVideo, double seconds) {
//     保留原声合并音视频 ffmpeg -i bgm.mp3 -i input.mp4 -t 6 -filter_complex amix=inputs=2 output.mp4
        try {
            if(StrUtils.checkBlank(bgm) || StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputVideo) || seconds <= 0) {
                throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
            }
            BaseFileUtil.checkAndMkdir(outputVideo);

            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-i");
            commands.add(bgm);

            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-t");
            commands.add(String.valueOf(seconds));

            commands.add("-filter_complex");
            commands.add("amix=inputs=2");

            commands.add("-y");
            commands.add(outputVideo);

            ProcessBuilder builder= new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 对视频进行截取，获取视频封面图
     *
     * @param inputVideo 原始视频绝对路径
     * @param coverOut 图片输出路径
     * @return
     */
    public Result getVideoCoverImg(String inputVideo, String coverOut) {
//        ffmpeg -ss 00:00:01 -y -i input.mp4 -vframes 1 out.jpg
        try {
            if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(coverOut)) {
                throw new FFMpegExceptionn("请输入视频路径或封面图片输入参数");
            }
            BaseFileUtil.checkAndMkdir(coverOut);

            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-ss");
            commands.add("00:00:01");

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-vframes");
            commands.add("1");

            commands.add(coverOut);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 对视频的指定秒开始截图，可截多张图
     *
     * @param startSeconds 多少秒开始截图
     * @param inputVideo 需要截图的视频绝对路径
     * @param everySecondImg 每秒截多少张图
     * @param seconds 一共持续截图多少秒
     * @param coverOutPath 截图生成的路径（图片名称会以001 002... 命名）
     * @return
     */
    public Result getVideoCoverImgs(Integer startSeconds, String inputVideo,Integer everySecondImg, Integer seconds,String coverOutPath) {
//        ffmpeg -y -ss 0 -i 2222.mp4 -f image2 -r 1 -t 3 -q:a 1 ./%2d.jpg
        try {
            if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(coverOutPath) || everySecondImg <= 0 || startSeconds <= 0 || seconds <= 0) {
                throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
            }
            BaseFileUtil.checkAndMkdir(coverOutPath);

            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-ss");
            commands.add(String.valueOf(startSeconds));

            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-f");
            commands.add("image2");

            commands.add("-r");
            commands.add(String.valueOf(everySecondImg));

            commands.add("-t");
            commands.add(String.valueOf(seconds));

            commands.add("-q:a");
            commands.add("1");

            commands.add(coverOutPath + "/%3d.jpg");

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 去除视频的音频
     *
     * @param inputVideo 输入视频绝对路径
     * @param outputVideo 输出视频绝对路径
     * @return
     */
    public Result wipeAudio(String inputVideo, String outputVideo) {
//       ffmpeg -y -i source.mp4 -an -vcodec copy output.mp4
        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputVideo)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputVideo);

        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-an");
            commands.add("-vcodec");

            commands.add("copy");
            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 视频缩放
     *
     * @param inputVideo 需要操作的原始视频绝对路径
     * @param outWidth 处理之后的视频的宽度
     * @param outHeight 处理之后的视频高度
     * @param outputVideo 处理之后生成的新的视频绝对路径
     * @return
     */
    public Result videoScale(String inputVideo, String outWidth, String outHeight, String outputVideo) {
//       ffmpeg -y -i in.mp4 -vf scale=360:640 -acodec aac -vcodec h264 out.mp4

        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputVideo)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputVideo);

        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-vf");
            commands.add("scale="+ outWidth + ":" + outHeight);

            commands.add("-acodec");
            commands.add("aac");

            commands.add("-vcodec");
            commands.add("h264");

            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 视频的页面长宽进行裁剪
     *
     * @param inputVideo 需要操作的原始视频绝对路径
     * @param outWidth 处理之后的视频的宽度
     * @param outHeight 处理之后的视频高度
     * @param outputVideo 处理之后生成的新的视频绝对路径
     * @return
     */
    public Result videoCrop(String inputVideo, String outWidth, String outHeight, String x, String y, String outputVideo) {
//       ffmpeg -y -i in.mp4 -strict -2 -vf crop=1080:1080:0:420 out.mp4
        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputVideo)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputVideo);

        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-strict");
            commands.add("-2");

            commands.add("-vf");
            commands.add("crop=" + outWidth + ":" + outHeight + ":" + x + ":" + y);

            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 视频角度旋转
     *
     * @param inputVideo 需要操作的原始视频绝对路径
     * @param angleNum 旋转的角度，1：180度 2：90度
     * @param outWidth 输出视频的宽度，如果不指定，默认是输入视频的宽度
     * @param outHeight 输出视频的高度，如果不指定，默认是输入视频的高度
     * @param outputVideo 处理之后生成的新的视频绝对路径
     * @return
     */
    public Result videoRotate(String inputVideo, Integer angleNum, String outWidth, String outHeight, String outputVideo) {
//       ffmpeg -i in.mp4 -vf rotate=PI/2:ow=1080:oh=1920 out.mp4
        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputVideo)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputVideo);

        if(angleNum != 1 && angleNum != 2) {
            throw new FFMpegExceptionn("非法参数，旋转角度需为-> 1：180deg or 2：90deg");
        }

        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-vf");

            if(StrUtils.checkNotBlank(outWidth) && StrUtils.checkNotBlank(outHeight)) {
                commands.add("rotate=PI/"+ angleNum + ":ow=" + outWidth + ":oh" + outHeight);
            } else {
                commands.add("rotate=PI/"+ angleNum);
            }

            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 调节视频帧数
     *
     * @param inputVideo 需要操作的原始视频绝对路径
     * @param fps 需要调节到多少帧
     * @param outputVideo 处理之后生成的新的视频绝对路径
     * @return
     */
    public Result videoFps(String inputVideo, Integer fps, String outputVideo) {
//      ffmpeg -y -i in.mp4 -r 15 out.mp4
        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputVideo)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputVideo);

        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            if(StrUtils.checkNotBlank(outputVideo)) {
                commands.add("-r");
                commands.add(String.valueOf(fps));
            }

            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * gif转换为video
     *
     * @param gif gif绝对路径
     * @param outputVideo 输出视频绝对路径
     * @return
     */
    public Result gifConvertToVideo(String gif, String outputVideo) {
//      ffmpeg -i in.gif -vf scale=420:-2,format=yuv420p out.mp4  // gif --> mp4
        if(StrUtils.checkBlank(gif) || StrUtils.checkBlank(outputVideo)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputVideo);

        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(gif);

            commands.add("-vf");

            commands.add("scale=420:-2,format=yuv420p");

            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 视频转gif
     *
     * @param inputVideo 需操作视频的绝对路径
     * @param outputGif 生成gif的输出绝对路径
     * @param highQuality 是否生成高质量gif
     * @return
     */
    public Result videoConvertToGif(String inputVideo,  String outputGif, boolean highQuality) {
//      ffmpeg -i src.mp4 -b 2048k des.gif
//      ffmpeg -i src.mp4 des.gif
        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(outputGif)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputGif);
        try {
            BaseFileUtil.checkAndMkdir(outputGif);

            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-i");
            commands.add(inputVideo);

            if(highQuality) {
                commands.add("-b");
                commands.add("2048k");
            }

            commands.add(outputGif);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }


    /**
     * 对视频的播放时间进行裁剪
     *
     * @param inputVideo 原始需要操作视频的绝对路径
     * @param startTime 开始裁剪的时间 支持格式： 2  或  00:00:02 从2秒开始
     * @param seconds  剪裁持续的时间 支持格式： 3 或 00:00:03 持续3秒
     * @param outputVideo 输出视频的绝对路径
     * @return
     */
    public Result videoCut(String inputVideo,String startTime, String seconds, String outputVideo) {
//      ffmpeg -ss 10 -t 15 -accurate_seek -i test.mp4 -codec copy -avoid_negative_ts 1 cut.mp4
//      ffmpeg -i src.mp4  -ss 00:00:00 -t 00:00:20 des.mp4
        if(StrUtils.checkBlank(inputVideo) || StrUtils.checkBlank(startTime) || StrUtils.checkBlank(seconds) || StrUtils.checkBlank(outputVideo)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(outputVideo);

        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");
            commands.add("-ss");
            commands.add(startTime);

            commands.add("-t");
            commands.add(seconds);

            commands.add("-accurate_seek");

            commands.add("-i");
            commands.add(inputVideo);

            commands.add("-codec");
            commands.add("-copy");
            commands.add("-avoid_negative_ts");
            commands.add("1");

            commands.add(outputVideo);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     *  视频合并音频，给视频加上背景音乐，并不保留视频原声
     *
     * （此方法在Mac平台无效,ffmpeg version 4.2.1-tessus），暂不清楚是否ffmpeg版本问题
     *  Mac平台使用方法：convertorWithBgmNoOriginCommon()
     *
     * @param videoInputPath 原始视频绝对路径
     * @param videoOutPath  处理之后视频输出路径
     * @param bgmInputPath  添加的背景音乐绝对路径
     * @param seconds   输出视频的秒数
     * @return
     */
    public Result mergeVideoAndBgmNoOrigin(String videoInputPath, String videoOutPath, String bgmInputPath, double seconds) {
//        ffmpeg -i input.mp4 -i bgm.mp3 -t 7 -y out.mp4
        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-i");
            commands.add(videoInputPath);

            commands.add("-i");
            commands.add(bgmInputPath);

            commands.add("-t");
            commands.add(String.valueOf(seconds));

            commands.add("-y");
            commands.add(videoOutPath);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 视频合并音频，给视频加上背景音乐，并不保留视频原声，此方法比较通用，并且Mac可以使用
     *
     * @param videoInputPath  原始视频绝对路径
     * @param videoOutPath  处理之后视频输出路径
     * @param noSoundVideoPath  原始视频去除音频的输出绝对路径
     * @param bgmInputPath  添加的背景音乐绝对路径
     * @param seconds   输出视频的秒数
     * @return
     */
    public Result convertorWithBgmNoOriginCommon(String videoInputPath, String videoOutPath, String noSoundVideoPath, String bgmInputPath, double seconds) {
//        ffmpeg -i hi.mp4 -c:v copy -an nosound.mp4
//        ffmpeg -i nosound.mp4 -i songs.mp3 -t 7.1 -c copy output.mp4
        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-y");

            commands.add("-i");
            commands.add(videoInputPath);

            commands.add("-c:v");
            commands.add("copy");
            commands.add("-an");
            commands.add(noSoundVideoPath);

            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            StreamHanlerCommon.closeStreamQuietly(process);

            // 转换nosound.mp4 为背景音乐的mp4
            return convertNoSoundVideoToBgmVideo(videoOutPath,noSoundVideoPath,bgmInputPath,seconds);
        } catch (Exception e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

    /**
     * 将没有背景音乐的video转换为有背景音乐video
     * 无声视频加上背景音乐
     *
     * @param videoOutPath
     * @param noSoundVideoPath
     * @param bgmInputPath
     * @param seconds
     * @throws IOException
     */
    private Result convertNoSoundVideoToBgmVideo(String videoOutPath, String noSoundVideoPath,String bgmInputPath, double seconds) throws IOException {
        List<String> commands2 = new ArrayList<>();
        commands2.add(ffmpegEXE);

        commands2.add("-i");
        commands2.add(noSoundVideoPath);

        commands2.add("-i");
        commands2.add(bgmInputPath);

        commands2.add("-t");
        commands2.add(String.valueOf(seconds));

        commands2.add("-y");
        commands2.add(videoOutPath);

        ProcessBuilder builder2 = new ProcessBuilder(commands2);
        Process process2 = builder2.start();

        return StreamHanlerCommon.closeStreamQuietly(process2);
    }

    /**
     * 修改视频封面图片
     *
     * @param videoInputPath 原始视频绝对路径
     * @param imagePath  替换的封面图片绝对路径
     * @param videoOutPath  新的视频输出绝对路径
     * @return
     */
    public Result transformVideoCover(String videoInputPath, String imagePath, String videoOutPath) {
        // -y 参数表示覆盖同名称文件
        // ffmpeg -i x.mp4 -i 1.png -map 1 -map 0 -c copy -disposition:0 attached_pic -y y.mp4
        if(StrUtils.checkBlank(videoInputPath) || StrUtils.checkBlank(imagePath) || StrUtils.checkBlank(videoOutPath)) {
            throw new FFMpegExceptionn("请输入正确参数，参数不能为空");
        }
        BaseFileUtil.checkAndMkdir(videoOutPath);
        try {
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);

            commands.add("-i");
            commands.add(videoInputPath);

            commands.add("-i");
            commands.add(imagePath);

            commands.add("-map");
            commands.add("1");
            commands.add("-map");
            commands.add("0");
            commands.add("-c");
            commands.add("copy");
            commands.add("-disposition:0");
            commands.add("attached_pic");

            commands.add("-y");
            commands.add(videoOutPath);

            // TODO 使用单例模式、或者将该对象定义为静态属性变量即可，不用每次new
            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();

            return StreamHanlerCommon.closeStreamQuietly(process);
        } catch (IOException e) {
            throw new FFMpegExceptionn(e.getMessage());
        }
    }

}
