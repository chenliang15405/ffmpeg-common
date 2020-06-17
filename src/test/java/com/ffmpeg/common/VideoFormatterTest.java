package com.ffmpeg.common;

import com.ffmpeg.common.video.VideoFormatter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author alan.chen
 * @date 2020/6/10 10:18 AM
 */
public class VideoFormatterTest {


    @Test
    public void createTempVideoFileTest() {
        String dir = "/Documents/notes/test/dir";
        File tempVideoFile = null;
        try {
            tempVideoFile = VideoFormatter.createTempVideoFile(dir);

            Assert.assertTrue(tempVideoFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(tempVideoFile != null) {
                tempVideoFile.deleteOnExit();
            }
        }
    }

}
