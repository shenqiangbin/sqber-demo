package com.sqber.log4jdemo.controller;

import com.sqber.commonTool.SignUtil;
import com.sqber.commonWeb.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/share/v1")
public class TestController {
    @PostMapping("/dataMeta")
    public R dataMeta(String appKey, String nonce, Long time, String dataid) {
        return R.success("元数据");
    }

    @PostMapping("/dataCount")
    public R test(String appKey, String nonce, Long time, String dataid, String params) {
        return R.error("数据库连接失败");
    }

//    private boolean validate(String appKey, String nonce, Long time) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("appKey", "wxd930ea5d5a258f4f");
//        map.put("nonce", "ibuaiVcKdpRxkhJA");
//        map.put("time", System.currentTimeMillis());
//
//        map.put("dataid", "10000100");
//        map.put("params", params);
//
//        String appSecret = getAppSecret(appKey);
//        String result = SignUtil.sign(map, appSecret);
//    }
//
//    private String getAppSecret(String appKey) {
//        return "192006250b4c09247ec02edce69f6a2d";
//    }
}
