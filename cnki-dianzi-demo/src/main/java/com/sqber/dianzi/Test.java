package com.sqber.dianzi;

import com.sqber.commonTool.HttpHelper;
import com.sqber.commonTool.JSONUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    static final String ELE_URL_TOKEN = "http://e.dev.cnki.net/UserAuth/connect/token";
    static final String ELE_URL_TOKEN_ONLINE = "http://sso.cnki.net/UserAuth/connect/token";

    static final String APP_ID = "0e65b4081f54421b";
    static final String APP_ID_ONLINE = "0e65b4081f54421b";
    static final String APP_SECRET = "91e4ed64c0fb491ca0ce654f81172c0f";
    static final String APP_SECRET_ONLINE = "91e4ed64c0fb491ca0ce654f81172c0f";

    static final String USER_API_BASE = "http://e.dev.cnki.net/UserApi";
    static final String USER_API_BASE_ONLINE = "http://sso.cnki.net/UserApi";

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
