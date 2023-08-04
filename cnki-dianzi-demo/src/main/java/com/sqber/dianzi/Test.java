package com.sqber.dianzi;

import com.sqber.commonTool.HttpHelper;
import com.sqber.commonTool.JSONUtil;
import com.sqber.commonTool.Sm4Util;
import com.sqber.dianzi.result.DsBaseResult;
import com.sqber.dianzi.result.DsLoginResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 已拒绝为此请求授权。 : 请求接口时没有携带 Token
 */
public class Test {

    static final String APP_ID = "088565c70e6f4d5e";
    static final String APP_ID_ONLINE = "057ad3b6c3964ab8";
    static final String APP_SECRET = "ddb1a156f3c54848b5eda65ce5db5805";
    static final String APP_SECRET_ONLINE = "";

    /*   User apis */

    static final String ELE_URL_TOKEN = "http://e.dev.cnki.net/UserAuth/connect/token";
    static final String ELE_URL_TOKEN_ONLINE = "";

    static final String USER_API_BASE = "http://e.dev.cnki.net/UserApi";
    static final String USER_API_BASE_ONLINE = "";

    /*   apis */

    static final String API_URL_TOKEN = "http://e.dev.cnki.net/Auth/OAuth/Token";
    static final String API_URL_TOKEN_ONLINE = "http://oauth.cnki.net/Auth/OAuth/Token";

    static final String API_BASE = "http://e.dev.cnki.net/Apis";
    static final String API_BASE_ONLINE = "http://oauth.cnki.net:8450";

    static final String LOGIN = "/Login/Login";
    static final String REGISTER = "/Register/create";
    static final String GET_PARENT_USER = "/User/get_parent_users";
    static final String BIND_PARENT_USER = "/User/bind_user";
    static final String CHANGE_PWD = "/User/change_password";
    static final String RESET_PWD_BY_EMAIL = "/User/reset_random_password_by_email";

    /* 统一结算 */

    static final String UNIFORM_API_BASE_ONLINE = "https://bar.cnki.net";
    static final String UNIFORM_API_BASE = "http://e.dev.cnki.net";
    static final String UNIFORM_SETTLEMENT = "/bar/download/order";

    /*下载*/

    static final String DOWNLOAD_TOKEN_URL = "http://10.120.67.101:8083/oauth/token";
    static final String DOWNLOAD_TOKEN_URL_ONLINE = "http://10.120.67.101:8083/oauth/token";

    static final String DOWNLOAD_URL = "http://10.120.67.101:8087/docdown/api/read/down";
    static final String DOWNLOAD_URL_ONLINE = "";

    static final String DOWNLOAD_CLIENT_ID = "70159";
    static final String DOWNLOAD_CLIENT_ID_ONLINE = "";

    static final String DOWNLOAD_CLIENT_SECRET = "HJ9rKJP24NNqIu6r";
    static final String DOWNLOAD_CLIENT_SECRET_ONLINE = "";

    static boolean debug = true;

    public static void main(String[] args) throws Exception {
        //getUserApiToken();
        //getApiToken();

        //testRegisterApi();
        //testLoginApi();
        //testBindUserApi();
        //testChangPwd();
        //testResetPwd();
        //testUniformSettlement();

//        String re = URLEncoder.encode("+", "UTF-8");
//        System.out.println(re);
        testDownloadToken();
        System.out.println("end");
    }

    static void testUniformSettlement() throws Exception {

        String query = "platform=NZKPT&product=CDFD&filename=2008167170.nh&tablename=CDFD9908&type=CDFD&dflag=cajdown&pages=&catalog=&language=zh&timestamp=1606790488000&preplatform=kns&danpian=1&paysource=test";
        query = "platform=SZRW&product=SZRWDATA&filename=2008167170.nh&tablename=CDFD9908&type=CDFD&scope=readonline&pages=&catalog=123&language=zh&timestamp="+System.currentTimeMillis()+"&preplatform=&danpian=&paysource=";
        //query = "platform=NZKPT&product=CJFD&filename=ZHYJ201310028&tablename=CJFDZHYX&type=JOURNAL&dflag=caj&cflag=overlay&pages=&scope=download&language=chs&timestamp=1635305265000&preplatform=kns&danpian=0&paysource=5";
        //query = "platform=NZKPT&product=CJFD&filename=ZHYJ201310028&tablename=CJFDZHYX&type=JOURNAL&dflag=&cflag=&pages=&scope=download&language=chs&timestamp=1635835619000&preplatform=kns&danpian=0&paysource=5";
        //query = "sourcetype=zwdz&platform=NZKPT&product=CJFD&filename=ZHZL201909011&tablename=CJFDZHYX&type=JOURNAL&dflag=&cflag=&pages=&scope=download&language=chs&timestamp="+System.currentTimeMillis()+"&preplatform=kns&danpian=0&paysource=5";
        String key = "2gd#@sX4S@:ILlO{";
        String cipherBase64 = Sm4Util.encryptEcbToBase64(key, query);

        String encodecipherBase64 = encode(cipherBase64);

        String url = (debug ? UNIFORM_API_BASE : UNIFORM_API_BASE_ONLINE) + UNIFORM_SETTLEMENT + "?id=" + encodecipherBase64;
        System.out.println(url);

//        String val = HttpHelper.httpGet(url, null);
        System.out.println(cipherBase64);
        System.out.println(encodecipherBase64);
//        System.out.println(val);
    }

    static void testDownloadToken() throws IOException {

        Map<String, String> paramsMap = new HashMap<>();

        paramsMap.put("client_id", debug ? DOWNLOAD_CLIENT_ID : DOWNLOAD_CLIENT_ID_ONLINE);
        paramsMap.put("client_secret", debug ? DOWNLOAD_CLIENT_SECRET : DOWNLOAD_CLIENT_SECRET_ONLINE);
        paramsMap.put("grant_type", "client_credentials");

        String url = debug ? DOWNLOAD_TOKEN_URL : DOWNLOAD_TOKEN_URL_ONLINE;
        String val = HttpHelper.httpPost(url,paramsMap,null);

        System.out.println(val);

        TokenResult tokenResult = JSONUtil.toObject(val, TokenResult.class);

        System.out.println(val);

        testDownload(tokenResult.getAccess_token(), "GHZH201402002", "cjfd2014", "JOURNAL",
                "5BA9B475DE1047CA9E049818E9DE89E9",
                "pGBKFKnqza70lf7dB15LNGhJscxFYRephP%2FhCDaFrscV4ncN%2FiJ%2FpFm81JGS5x9g3Sm0zW3rkYvWnbC1O0BQYJzBSphJQsbJuSYpLOZjxBeX0cd41tYwfhvgfUhWep8AmtTaCm2BU8WfNmi27S2nbPMZF66%2BthFtBZpsheELcQU%3D");

    }

    static void testDownload(String token, String filename, String tablename, String type,  String nonce, String invoice) throws IOException {

        String annexid = UUID.randomUUID().toString().replace("-","") + ".xml";
        annexid = filename + ".xml";
        String plateform = "SZRW";
        String product = "SZRWDATA";
        String scope = "readonline";

        String queryUrl = String.format("?platform=%s&product=%s&filename=%s&tablename=%s&type=%s&scope=readonline&dflag=&cflag=html&pages=&language=CHS&nonce=%s&annexid=%s&invoice=%s",
                plateform,
                product,
                filename,
                tablename,
                type,
                nonce,
                annexid,
                invoice);


        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", "Bearer " + token);

        String url = (debug ? DOWNLOAD_URL : DOWNLOAD_URL_ONLINE) + queryUrl;
        //url = "http://10.120.67.101:8087/docdown/api/read/down?platform=NZKPT&product=CDFD&filename=2008033916.nh&tablename=cdfd2008&type=DISSERTATION&scope=readonline&dflag=&cflag=html&pages=&language=CHS&nonce=1922CC438E4642A5802FE8E293504FAF&annexid=2008033916.xml&invoice=Do6169aTvXcuQmUca9VO9Yirw2/y/wFiqf5nj/0DVAQFv9pVu3JnRIKHXcgbT4vRsGzV4KETaam/%2bFKBGpYUElIYGkgJjla6bM8dg%2b%2bXeDbdiZQWWpJJq6LsYoJBeRP2DEZzXtYiW5vw%2bN93NbXSqHxGux0cMcAUCYi%2bxXLVq6Q=";
        String val = HttpHelper.httpGet(url,headMap);

        System.out.println(val);


    }

    static void getUserApiToken() throws IOException {

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

    static String getApiToken() throws IOException {

        Map<String, String> paramsMap = new HashMap<>();

        paramsMap.put("client_id", debug ? APP_ID : APP_ID_ONLINE);
        paramsMap.put("client_secret", debug ? APP_SECRET : APP_SECRET_ONLINE);
        paramsMap.put("grant_type", "client_credentials");

        String val = HttpHelper.httpPost(debug ? API_URL_TOKEN : API_URL_TOKEN_ONLINE, paramsMap, null);

        TokenResult tokenResult = JSONUtil.toObject(val, TokenResult.class);

        System.out.println(tokenResult.getAccess_token());
        System.out.println(tokenResult.getExpires_in());
        System.out.println(tokenResult.getToken_type());

        return tokenResult.getAccess_token();
    }

    static void testRegisterApi() throws IOException {

        Map<String, String> paramsMap = new HashMap<>();

//        paramsMap.put("regType", "email");
//        paramsMap.put("username", "szrwuser001");
//        paramsMap.put("password", "szrwuser001");
//        paramsMap.put("ip", "124.193.98.154");
//        paramsMap.put("email", "1348907384@qq.com");
//        paramsMap.put("mobile", "");
//        paramsMap.put("verifyCode", "");
//        paramsMap.put("platform", "");
//        paramsMap.put("superPassword", "");
//        paramsMap.put("superPassword", "");

        String queryUrl = String.format("?regType=email&username=%s&password=%s&ip=%s&email=%s",
                "saadmin123", "cnkittod123", "124.193.98.154", "1348907384@qq.com");
        String val = HttpHelper.httpPost(getAPIUrl(REGISTER) + queryUrl, paramsMap, getHead());

        // {"Success":true,"Code":0,"Message":"501031946"}
        //{"Success":true,"Code":0,"Message":"1082172983"} online
        System.out.println(val);

        DsBaseResult result = JSONUtil.toObject(val, DsBaseResult.class);
        System.out.println(result.getMessage());
    }

    static void testLoginApi() throws IOException {

        // saadmin123  cnkittod123
        String queryUrl = String.format("?username=%s&password=%s&ip=%s",
                "szrwuser001", "FG4gPwbpu0i5", "124.193.98.1");
        queryUrl = String.format("?username=%s&password=%s&ip=%s","saadmin", "cnkittod", "124.193.98.154");
        Map<String, String> paramsMap = new HashMap<>();
        String val = HttpHelper.httpPost(getAPIUrl(LOGIN) + queryUrl, paramsMap, getHead());

        //{"Success":false,"Code":5005,"Message":"登录失败，没有该用户！","IdenId":"","Username":"","PersonUserName":"","InstUserName":"","InstShowName":""}
        System.out.println(val);

        DsLoginResult loginResult = JSONUtil.toObject(val, DsLoginResult.class);
        System.out.println(loginResult.getMessage());
        if (loginResult.isSuccess()) {
            System.out.println("login success");

            testGetParentUser(loginResult.getIdenId(), "124.193.98.1");

        } else {
            System.out.println("login fail");
            System.out.println(loginResult.getMessage());
        }
    }

    // 绑定用户（缺少一个机构账号）
    static void testBindUserApi() throws IOException {

        //"saadmin123", "cnkittod123"
        String user = "szrwuser001";
        user = "saadmin123";
        //user = "quyuuserstest7";
        String parentUser = "szrwjigou";
        parentUser = "TTOD"; //dyy7292
        String parentPwd = "szrwjigou";

        String queryUrl = String.format("?username=%s&parentName=%s&bindType=1", user, parentUser);
        Map<String, String> paramsMap = new HashMap<>();
        String val = HttpHelper.httpPost(getAPIUrl(BIND_PARENT_USER) + queryUrl, paramsMap, getHead());

        //{"Success":false,"Code":5005,"Message":"登录失败，没有该用户！","IdenId":"","Username":"","PersonUserName":"","InstUserName":"","InstShowName":""}
        System.out.println(val);

//        DsLoginResult loginResult = JSONUtil.toObject(val, DsLoginResult.class);
//        System.out.println(loginResult.getMessage());
//        if (loginResult.isSuccess()) {
//            System.out.println("login success");
//        } else {
//            System.out.println("login fail");
//            System.out.println(loginResult.getMessage());
//        }
    }

    static void testGetParentUser(String idenId, String ip) throws IOException {
        String queryUrl = String.format("?idenId=%s&userIP=%s", idenId, ip);
        Map<String, String> paramsMap = new HashMap<>();
        String val = HttpHelper.httpGet(getAPIUrl(GET_PARENT_USER) + queryUrl, getHead());

        System.out.println(val);
    }

    static void testChangPwd() throws IOException {

        String user = "quyuuserstest8";
        String pwd = "Jt201912";
        String newPwd = "123456";

        String queryUrl = String.format("?username=%s&password=%s&newPassword=%s", user, pwd, newPwd);
        Map<String, String> paramsMap = new HashMap<>();
        String val = HttpHelper.httpPost(getAPIUrl(CHANGE_PWD) + queryUrl, paramsMap, getHead());

        //{"Success":false,"Code":5005,"Message":"登录失败，没有该用户！","IdenId":"","Username":"","PersonUserName":"","InstUserName":"","InstShowName":""}
        System.out.println(val);

//        DsLoginResult loginResult = JSONUtil.toObject(val, DsLoginResult.class);
//        System.out.println(loginResult.getMessage());
//        if (loginResult.isSuccess()) {
//            System.out.println("login success");
//        } else {
//            System.out.println("login fail");
//            System.out.println(loginResult.getMessage());
//        }
    }

    static void testResetPwd() throws IOException {

        String user = "szrwuser001";
        String email = "1348907384@qq.com";
        String plateFormInfo = "CNKI数字人文研究平台";
        String userIp = "192.168.25.150";


        String queryUrl = String.format("?username=%s&email=%s&platFormInfo=%s&userIP=%s", encode(user), encode(email), encode(plateFormInfo), encode(userIp));
        Map<String, String> paramsMap = new HashMap<>();
        String val = HttpHelper.httpPost(getAPIUrl(RESET_PWD_BY_EMAIL) + queryUrl, paramsMap, getHead());

        //{"Success":false,"Code":5005,"Message":"登录失败，没有该用户！","IdenId":"","Username":"","PersonUserName":"","InstUserName":"","InstShowName":""}
        System.out.println(val);

//        DsLoginResult loginResult = JSONUtil.toObject(val, DsLoginResult.class);
//        System.out.println(loginResult.getMessage());
//        if (loginResult.isSuccess()) {
//            System.out.println("login success");
//        } else {
//            System.out.println("login fail");
//            System.out.println(loginResult.getMessage());
//        }
    }

    static String getAPIUrl(String url) {
        return (debug ? API_BASE : API_BASE_ONLINE) + url;
    }

    static Map getHead() throws IOException {
        Map<String, String> headMap = new HashMap<>();

        String apiToken = getApiToken();
        headMap.put("Authorization", "Bearer " + apiToken);

        return headMap;
    }

    static String encode(String content) throws UnsupportedEncodingException {
        return URLEncoder.encode(content, "UTF-8");
    }
}
