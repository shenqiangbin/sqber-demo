package com.sqber.otherdemo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SignInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String method = request.getMethod();
        String queryString = request.getQueryString();
        Map<String, String[]> parameterMap = request.getParameterMap();

        if (!parameterMap.containsKey("appKey")) {
            throw new ValidationException("appKey不存在");
        }
        if (!parameterMap.containsKey("nonce")) {
            throw new ValidationException("nonce不存在");
        }
        if (!parameterMap.containsKey("timestamp")) {
            throw new ValidationException("timestamp不存在");
        }
        if (!parameterMap.containsKey("sign")) {
            throw new ValidationException("sign不存在");
        }

        String sortStr = getSortStr(parameterMap);


        System.out.println("ok");
        return true;
    }

    public static String getSortStr(Map<String, String[]> map) {
        List<String> keys = map.keySet().stream().filter(m -> !m.equals("sign")).collect(Collectors.toList());
        Collections.sort(keys);
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            String value = map.get(key) == null ? "" : map.get(key)[0].toString();
            if (i == keys.size() - 1) {
                content.append(key).append("=").append(value);
            } else {
                content.append(key).append("=").append(value).append("&");
            }
        }
        return content.toString();
    }
}