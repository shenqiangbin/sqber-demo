package com.sqber.dianzi.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DsBaseResult {

    @JsonProperty("Success")
    public boolean success;

    @JsonProperty("Code")
    private int code;

    @JsonProperty("Message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
