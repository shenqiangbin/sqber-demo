package com.sqber.commonTool.excel;

import java.util.List;

/**
 * EXCEL 的保存结果类
 * @author sqber
 */
public class SaveResult {
    private boolean success;
    private String msg;
    private List<List<String>> data;

    public SaveResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public SaveResult(List<List<String>> data) {
        this.success = true;
        this.msg = "";
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public List<List<String>> getData() {
        return data;
    }


}
