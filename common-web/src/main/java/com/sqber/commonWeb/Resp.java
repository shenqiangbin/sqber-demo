package com.sqber.commonWeb;

/**
 * 统一结果类
 *
 */
public class Resp {

    private static final int SUCCESS = 200;
    private static final int ERROR = 500;
    private static final int ValidateFailure = 410;

    private int code;
    private Object data;
    private String msg;

    private Resp(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 参数验证失败
     *
     * @param msg
     * @return
     */
    public static Resp warn(String msg) {
        return new Resp(ValidateFailure, msg, "");
    }

    /**
     * 出错
     *
     * @param msg
     * @return
     */
    public static Resp error(String msg) {
        return new Resp(ERROR, msg, "");
    }

    /**
     * 出错
     *
     * @return
     */
    public static Resp error() {
        return new Resp(ERROR, "服务繁忙,请稍后重试", "");
    }


    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> Resp success() {
        return new Resp(SUCCESS, "", "");
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resp success(T data) {
        return new Resp(SUCCESS, "", data);
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static Resp success(Object data, String msg) {
        return new Resp(SUCCESS, msg, data);
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
