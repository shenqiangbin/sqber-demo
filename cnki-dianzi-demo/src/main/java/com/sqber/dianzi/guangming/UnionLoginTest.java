package com.sqber.dianzi.guangming;

import com.sqber.commonTool.HttpHelper;
import com.sqber.commonTool.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一登录测试
 */
public class UnionLoginTest {
    public static void main(String[] args) throws IOException {
        login();
    }

    private static void login() throws IOException {

        LoginInfoModel model = new LoginInfoModel();
        model.setKey("key");
        model.setUserName("name");
        model.setUserIp("192.168.20.151");
        model.setUserReturnUrl("http://www.sqber.com");
        String json = JSONUtil.toString(model);

        String url = "https://my.cnki.net/IntegrateLogin/api/IntegrateLoginApi/UserLogin";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("content-type","application/json");

        String val = HttpHelper.httpPostJSON(url, json, paramsMap);

        LoginResult tokenResult = JSONUtil.toObject(val, LoginResult.class);

        String returlUrl = "https://my.cnki.net/IntegrateLogin/Redirect.ashx";

        if (tokenResult.getCode() == "1")
        {
            String openUrl = String.format("%s?token=%s", returlUrl, tokenResult.getToken());
            if (StringUtils.isNotBlank(model.getUserReturnUrl()))
            {
                openUrl += "&ReturnUrl=" + model.getUserReturnUrl();
            }
            System.out.println(openUrl);
        }
    }
}
