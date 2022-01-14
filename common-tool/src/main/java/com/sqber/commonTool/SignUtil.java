package com.sqber.commonTool;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.io.IOException;
import java.util.*;

public class SignUtil {

    public static void main(String[] args) throws IOException {
        test();
        //testWx();
    }

    public static void test() throws IOException {

        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("name", "test");
        paraMap.put("age", "1000");
        String json = JSONUtil.toString(paraMap);

        Map<String, Object> map = new HashMap<>();
        map.put("dataid", "10000100");
        map.put("params", json);

        String url = "/share/v1/dataMeta";
        post(url, map);
    }

    public static void post(String url, Map<String, Object> paraMap) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("appKey", "wxd930ea5d5a258f4f");
        map.put("nonce", "ibuaiVcKdpRxkhJA");
        long time = System.currentTimeMillis();
        time = 1637714827254L;
        map.put("timestamp", time);

        map.putAll(paraMap);

        String appSecret = "192006250b4c09247ec02edce69f6a2d";
        String result = sign(map, appSecret);
        System.out.println(result);
        //00451B052AD905C4D5C8A665D09BB20FB50912AC4BBA28AD0967DE3A7E467175

        map.put("sign", result);

        HttpHelper.httpPost(url, map, null);
    }

    public static void testWx() {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", "wxd930ea5d5a258f4f");
        map.put("nonce_str", "ibuaiVcKdpRxkhJA");
        //map.put("time",System.currentTimeMillis());
        map.put("mch_id", "10000100");
        map.put("device_info", "1000");
        map.put("body", "test");
        String appSecret = "192006250b4c09247ec02edce69f6a2d";
        String result = sign(map, appSecret);
        System.out.println(result); // result: 6A9AE1657590FD6257D693A078E1C3E4BB6BA4DC30B23E0EE2496E54170DACD6
    }

    public static String sign(Map<String, Object> map, String key) {
        String sortStr = getSortStr(map);
        String str = sortStr + "&key=" + key;
        String hmacSha256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(str).toUpperCase();
        return hmacSha256;
    }

    public static String getSortStr(Map<String, Object> map) {
        List<String> keys = new ArrayList(map.keySet());
        Collections.sort(keys);
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            String value = map.get(key).toString();
            if (i == keys.size() - 1) {
                content.append(key).append("=").append(value);
            } else {
                content.append(key).append("=").append(value).append("&");
            }
        }
        return content.toString();
    }
}
