package com.sqber.commonTool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * List工具类
 * @author sqber
 */
public class ListUtil {

    /**
     * 将 List<List<String>> 类型的数据转换成对象列表
     * <p>注意</p>
     * <p><strong>目标类的字段顺序和个数 要与列表中数据相同</strong></p>
     * @param data List<List<String>> 的数据
     * @param type 目标类
     * @param <T> 目标类
     * @return 对象列表
     * @throws InstantiationException InstantiationException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static <T> List<T> toList(List<List<String>> data, Class<T> type) throws InstantiationException, IllegalAccessException {

        if (!(data != null && data.size() > 0)) {
            return new ArrayList<>();
        }

        List<T> list = new ArrayList<>();

        for (List<String> item : data) {
            list.add(toOne(item, type));
        }
        return list;
    }

    private static <T> T toOne(List<String> item, Class<T> type) throws IllegalAccessException, InstantiationException {
        // 此类要有默认的构造函数
        T instance = type.newInstance();
        Field[] fields = type.getDeclaredFields();

        for (int i = 0; i < item.size(); i++) {
            Field field = fields[i];

            if (field == null) {
                throw new RuntimeException(type.getName() + "字段个数不够");
            }

            field.setAccessible(true);
            field.set(instance, item.get(i));
        }

        return instance;
    }

}
