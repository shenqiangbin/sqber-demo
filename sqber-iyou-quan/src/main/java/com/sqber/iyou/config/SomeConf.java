package com.sqber.iyou.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SomeConf {

    @Value("${time.hour}")
    private int hour;

    @Value("${time.minute}")
    private int minute;

    @Value("${quan.number}")
    private String quanNumber;

    @Value("${adb.path}")
    private String adbPath;

    @Value("${adb.server}")
    private String server;

    @Value("${adb.server2}")
    private String server2;

    @Value("${btn.middle}")
    private String middleBtn;

    @Value("${btn.left}")
    private String leftBtn;

    @Value("${btn.quan}")
    private String quanBtn;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getQuanNumber() {
        return quanNumber;
    }

    public void setQuanNumber(String quanNumber) {
        this.quanNumber = quanNumber;
    }

    public String getAdbPath() {
        return adbPath;
    }

    public void setAdbPath(String adbPath) {
        this.adbPath = adbPath;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getMiddleBtn() {
        return middleBtn;
    }

    public void setMiddleBtn(String middleBtn) {
        this.middleBtn = middleBtn;
    }

    public String getQuanBtn() {
        return quanBtn;
    }

    public void setQuanBtn(String quanBtn) {
        this.quanBtn = quanBtn;
    }

    public String getLeftBtn() {
        return leftBtn;
    }

    public void setLeftBtn(String leftBtn) {
        this.leftBtn = leftBtn;
    }

    public String getServer2() {
        return server2;
    }

    public void setServer2(String server2) {
        this.server2 = server2;
    }
}
