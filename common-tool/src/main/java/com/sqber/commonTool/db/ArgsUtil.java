package com.sqber.commonTool.db;

public class ArgsUtil {
    public static String create(int size) {
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = "?";
        }
        return String.join(",", arr);
    }
}
