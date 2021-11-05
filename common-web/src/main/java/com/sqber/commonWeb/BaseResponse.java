package com.sqber.commonWeb;

public class BaseResponse<T> {

    private static final int SUCCESS = 200;
    private static final int ERROR = 500;
    private static final int ValidateFailure = 410;

    private int code;
    private T data;
    private String msg;

    private BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 参数验证失败
     * @param msg
     * @return
     */
    public static BaseResponse warn(String msg) {
        return new BaseResponse(ValidateFailure, msg, "");
    }

    /**
     * 出错
     * @param msg
     * @return
     */
    public static BaseResponse error(String msg) {
        return new BaseResponse(ERROR, msg, "");
    }


    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse success(T data) {
        return new BaseResponse(SUCCESS, "", data);
    }

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse success(T data, String msg) {
        return new BaseResponse(SUCCESS, msg, data);
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
