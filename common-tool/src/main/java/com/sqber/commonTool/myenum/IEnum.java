package com.sqber.commonTool.myenum;

public interface IEnum {
    String getName();

    Integer getVal();

    public static <T extends IEnum> T getEnumByVal(Class<T> clazz, int value) {
        for (T _enum : clazz.getEnumConstants()) {
            if (value == _enum.getVal()) {
                return _enum;
            }
        }
        throw new RuntimeException("没有对应的数值[" + clazz.toString() + ":" + String.valueOf(value) + "]");
    }

    /**
     * 根据【值】获取【对应的描述】
     *
     * @param clazz
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends IEnum> String geNameByVal(Class<T> clazz, int value) {
        T enumByVal = IEnum.getEnumByVal(clazz, value);
        return enumByVal.getName();
    }

}
