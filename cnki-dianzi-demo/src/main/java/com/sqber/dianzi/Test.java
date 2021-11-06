package com.sqber.dianzi;

import com.sqber.commonTool.HttpHelper;
import com.sqber.commonTool.JSONUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    static final String ELE_URL_TOKEN = "http://e.dev.cnki.net/UserAuth/connect/token";
    static final String ELE_URL_TOKEN_ONLINE = "";

    static final String APP_ID = "";
    static final String APP_ID_ONLINE = "";
    static final String APP_SECRET = "";
    static final String APP_SECRET_ONLINE = "";

    static final String USER_API_BASE = "http://e.dev.cnki.net/UserApi";
    static final String USER_API_BASE_ONLINE = "";

    static boolean debug = true;

    public static void main(String[] args) throws IOException {
        testLogin();
    }

    static void testLogin() throws IOException {

        Map<String, String> paramsMap = new HashMap<>();

        paramsMap.put("client_id", debug ? APP_ID : APP_ID_ONLINE);
        paramsMap.put("client_secret", debug ? APP_SECRET : APP_SECRET_ONLINE);
        paramsMap.put("grant_type", "client_credentials");
        paramsMap.put("scope", "api");

        String val = HttpHelper.httpPost(debug ? ELE_URL_TOKEN : ELE_URL_TOKEN_ONLINE, paramsMap, null);

        TokenResult tokenResult = JSONUtil.toObject(val, TokenResult.class);

        System.out.println(tokenResult.getAccess_token());
        System.out.println(tokenResult.getExpires_in());
        System.out.println(tokenResult.getToken_type());
    }

    static void testApi(){

    }
}
