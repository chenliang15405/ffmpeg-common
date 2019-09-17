package com.ffmpeg.common.response;

/**
 * @auther alan.chen
 * @time 2019/9/11 1:51 PM
 */
public class Result {

    /**
     * 状态码： 0 正常 其他：异常
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String errMessage;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
