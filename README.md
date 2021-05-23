# ffmpeg-common
**java关于ffmpeg命令实现音频、视频等操作的封装**

> 提供VideoOperation、AudioOperation操作工具类，方便实现对视频、音频等裁剪、合成、视频音频的拼接等一系列操作，方便操作和调用简单，需自定义ffmpeg的路径，因为后续可能出现ffmpeg版本等问题，所以自定义ffmpeg灵活可靠，提供更好的兼容性.

下载ffmpeg的地址： [ffmpeg下载](http://ffmpeg.org)

### 引入方式：

> 支持通过maven或者直接引入jar包的方式

- jar包请到maven仓库中下载即可

    [maven仓库地址](https://mvnrepository.com/artifact/com.github.chenliang15405/ffmpeg-common/3.2)

- maven构建依赖：

    ```xml
    <dependency>
        <groupId>com.github.chenliang15405</groupId>
        <artifactId>ffmpeg-common</artifactId>
        <version>3.2</version>
    </dependency>
    ```

## 1. VideoOperation 说明
> 视频操作对象，`VideoOperation`提供了操作视频的多种API

- **使用示例：**
    
    ```java
       public void testConverTest() throws IOException {
          // 指定ffmpeg的绝对路径
          String ffmpegEXE = "/server/notes/ffmpeg";
          String inputPath = "/server/notes/test/2222.mp4";
          String outPutPath = "/server/test/1/convert.flv";
          // 创建VideoOperation对象
          VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
          // 将mp4格式转换为flv格式
          Result result = ffmpeg.videoConvert(inputPath, outPutPath);  
          // 返回结果为0表示正确
          Assert.assertEquals(0, result.getCode());
          // 转换的信息
          System.out.println(result.getErrMessage());  
       }
      
    ```
- **VideoOperation API列表及说明：**

    | API | 参数说明 | 方法说明 |
    | ------ | ------ | ------ |
    | videoConvert(String inputVideo, String outputVideo) | inputVideo 原始视频绝对路径（带视频名称）(xxx/1.mp4) <br/> outputVideo  输出视频绝对路径（带视频名称）(xxx/1.flv) | 转换视频格式 |
    | mergeVideoAndBgmWithOrigin(String bgm, String inputVideo, String outputVideo, double seconds) | bgm: 背景音乐路径<br>inputVideo: 输入音频路径<br>outputVideo: 输出视频路径<br> seconds: 输出视频秒数 | 保留视频原声并增加背景音乐 |
    | getVideoCoverImg(String inputVideo, String coverOut) | inputVideo: 原始视频绝对路径<br> coverOut: 图片输出路径 | 获取视频的封面图 |
    | getVideoCoverImgs(Integer startSeconds, String inputVideo,Integer everySecondImg, Integer seconds,String coverOutPath) |  startSeconds: 多少秒开始截图<br/>  inputVideo: 需要截图的视频绝对路径 <br> everySecondImg 每秒截多少张图<br> seconds: 一共持续截图多少秒<br> seconds 一共持续截图多少秒 <br>  coverOutPath 截图生成的路径（图片名称会以001 002... 命名）| 对视频的指定秒开始截图，可截多张图 |
    | wipeAudio(String inputVideo, String outputVideo) | inputVideo: 输入视频绝对路径<br> outputVideo: 输出视频绝对路径 | 去除视频的音频 |
    | videoScale(String inputVideo, String outWidth, String outHeight, String outputVideo) | inputVideo: 需要操作的原始视频绝对路径<br> outWidth: 处理之后的视频的宽度<br> outHeight: 处理之后的视频高度<br>outputVideo: 处理之后生成的新的视频绝对路径 | 视频缩放 |
    | videoCrop(String inputVideo, String outWidth, String outHeight, String x, String y, String outputVideo) | inputVideo: 需要操作的原始视频绝对路径<br>outWidth: 处理之后的视频的宽度<br>outHeight: 处理之后的视频高度<br>outputVideo: 处理之后生成的新的视频绝对路径 | 视频的页面长宽进行裁剪 |
    | videoRotate(String inputVideo, Integer angleNum, String outWidth, String outHeight, String outputVideo) | inputVideo: 需要操作的原始视频绝对路径<br>angleNum: 旋转的角度，1：180度 2：90度<br>outWidth 输出视频的宽度，如果不指定，默认是输入视频的宽度<br>outHeight: 输出视频的高度，如果不指定，默认是输入视频的高度<br>outputVideo: 处理之后生成的新的视频绝对路径 | 视频角度旋转 |
    | videoFps(String inputVideo, Integer fps, String outputVideo) | inputVideo: 需要操作的原始视频绝对路径<br>fps: 需要调节到多少帧<br>outputVideo: 处理之后生成的新的视频绝对路径 | 调节视频帧数 |
    | gifConvertToVideo(String gif, String outputVideo) | gif: gif绝对路径<br>outputVideo: 输出视频绝对路径 | gif转换为video |
    | videoConvertToGif(String inputVideo,  String outputGif, boolean highQuality) | inputVideo: 需操作视频的绝对路径<br>outputGif: 生成gif的输出绝对路径<br>highQuality 是否生成高质量gif | 视频转gif |
    | videoCut(String inputVideo,String startTime, String seconds, String outputVideo) | inputVideo: 原始需要操作视频的绝对路径<br>startTime 开始裁剪的时间 支持格式： 2  或  00:00:02 从2秒开始<br>seconds  剪裁持续的时间 支持格式： 3 或 00:00:03 持续3秒<br>outputVideo 输出视频的绝对路径 | 对视频的播放时间进行裁剪 |
    | mergeVideoAndBgmNoOrigin(String videoInputPath, String videoOutPath, String bgmInputPath, double seconds) | videoInputPath: 原始视频绝对路径<br>videoOutPath  处理之后视频输出路径<br>bgmInputPath  添加的背景音乐绝对路径<br> seconds   输出视频的秒数 | 视频合并音频，给视频加上背景音乐，并不保留视频原声(此方法在Mac平台无效) |
    | convertorWithBgmNoOriginCommon(String videoInputPath, String videoOutPath, String noSoundVideoPath, String bgmInputPath, double seconds) | videoInputPath: 原始视频绝对路径<br>videoOutPath: 处理之后视频输出路径<br>noSoundVideoPath: 原始视频去除音频的输出绝对路径<br>bgmInputPath: 添加的背景音乐绝对路径<br>seconds: 输出视频的秒数 | 视频合并音频，给视频加上背景音乐，并不保留视频原声，此方法比较通用，并且Mac可以使用 |
    | transformVideoCover(String videoInputPath, String imagePath, String videoOutPath) | videoInputPath: 原始视频绝对路径<br>imagePath  替换的封面图片绝对路径<br>videoOutPath 新的视频输出路径<br> | 修改视频封面图片 |
    | mergeMultiOnlineVideos(File videoListFile, String videoOutPath) | videoListFile: 绝对路径下的视频list文件（参考sample文件：docs/video-example/video-online-example.txt）<br>videoOutPath: 新视频输出路径<br> | 合并多个在线视频（ts格式）, 根据文件中在线视频地址顺序合并（（参考sample文件：docs/video-example/video-online-example.txt）） |
    | mergeMultiVideosOfTsOrMpegFormat(List<String> fileNameList, String videoOutPath) | fileNameList: 需要合并的视频文件集合，文件名称为绝对路径<br>videoOutPath: 视频输出绝对路径 | 合并多个视频文件(此方法只适用ts格式文件,或者mpg/mpeg格式文件) |
    | mergeMultiVideosByFile(File videoListFile, String videoOutPath) | videoListFile: 定义合并视频的文件，按照指定格式访问（文件示例：docs/video-example/video-example.txt）<br>videoOutPath: 视频输出绝对路径文件名| 根据自定义文件中定义视频绝对路径信息，按照顺序合并视频（文件示例：docs/video-example/video-example.txt） |
    | autoMergeMultiVideosByDir(String dir, String videoOutPath) | dir: 视频文件目录绝对路径<br>videoOutPath: 视频输出绝对路径 | 根据文件目录，自动合并该目录下所有视频（默认合成的顺序按照文件名称进行排序）|

- **合并视频注意事项**
  - 合并的多个视频必须相同的分辨率，否则合成的视频会黑屏

## 2. AudioOperation 说明
> 音频操作对象，`AudioOperation`提供了操作视频的多种API

- **使用示例：**
    ```java
      public void audioFormatTest() {
         // 指定ffmpeg的绝对路径
         String ffmpegEXE = "/server/notes/ffmpeg";
         String inputPath = "/server/notes/VideoTest/amr.amr";
         String outPutPath = "/server/notes/VideoTest/mp3.mp3";
         // 创建AudioOperation对象
         AudioOperation ffmpeg = AudioOperation.builder(ffmpegEXE);
         // 转换为音频的amr格式为mp3格式
         Result result = ffmpeg.transFormatToMp3Audio(inputPath, outPutPath);
         // 返回结果为0表示转换成功
         Assert.assertEquals(0, result.getCode());
         // 转换的信息
         System.out.println(result.getErrMessage());
      }

  ```

- **AudioOperation API列表及说明：**

    | API | 参数说明 | 方法说明 |
    | ------ | ------ | ------ |
    | audioConcat(String bgmOutPath, String... bgmInputPath) | bgmOutPath: 输出音频文件<br>bgmInputPath 输入的音频文件, 多值参数 | 将多个音频文件拼接为一个音频文件并输出 |
    | audioCut(String bgmInputPath, String bgmOutPath, String startTime, String endTime) | bgmInputPath 音频输入绝对路径<br>bgmOutPath 音频输出绝对路径<br>startTime: 截取的开始时间(例如：00:00:00)<br>endTime: 截取的结束时间(例如：00:06:38) | 通过指定开始时间和结束时间 裁剪音频 |
    | getBgmFromVideo(String inputVideo, String outAudio) | inputVideo：视频绝对路径<br>outAudio: 输出音频绝对路径 | 从视频中提取音频 |
    | transFormatAudio(String inputAudio, String outAudio) | inputAudio: 输入视频绝对路径<br>outAudio: 输出音频绝对路径 | 转换音频格式 |
    | transFormatAmrAudio(String inputAudio, String outAudio) | inputAudio: 输入视频/音频绝对路径<br>outAudio 输出音频绝对路径 | 将其他格式的音频或视频转成AMR |
    | transFormatToMp3Audio(String inputAudio, String outAudio) | inputAudio 输入视频绝对路径<br>outAudio 输出音频绝对路径 | 转换音频格式 mp3编码方式采用的是libmp3lame |
    

**3.返回结果说明**
- Result对象
    - code 表示返回的状态码： 0 表示正常
    - errMessage 表示转换的信息： 不用于判断，是ffmpeg的转换过程产生的信息
