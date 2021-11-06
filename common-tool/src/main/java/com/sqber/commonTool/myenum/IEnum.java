package com.sqber.commonTool.myenum;

public interface IEnum {
    String getName();

    Integer getValue();

    public static <T extends IEnum> T getEnumByVal(Class<T> clazz, int value) {
        for (T _enum : clazz.getEnumConstants()) {
            if (value == _enum.getValue()) {
                return _enum;
            }
        }
        throw new RuntimeException("没有对应的数值[" + clazz.toString() + ":" + String.valueOf(value) + "]");
    }

}
