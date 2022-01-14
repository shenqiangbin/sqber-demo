package com.sqber.commonTool;

import org.apache.commons.io.FilenameUtils;

/**
 * 路径工具类
 */
public class PathUtil {

    /**
     * 在文件所在路径生成一个新的文件，返回文件路径
     *
     * @param filePath 文件位置
     * @return 新的文件路径
     */
    public static String getByPath(String filePath) {
        String guid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        return getByPath(filePath, guid);
    }

    /**
     * 在文件所在路径生成一个新的文件，返回文件路径
     *
     * @param filePath 文件位置
     * @param str      新文件名是原文件名后加 str
     * @return 新的文件路径
     */
    public static String getByPath(String filePath, String str) {
        String path = FilenameUtils.getFullPath(filePath);
        String filenameWithoutExt = FilenameUtils.getBaseName(filePath);
        String extension = FilenameUtils.getExtension(filePath);
        String newFileName = filenameWithoutExt + "_" + str + "." + extension;

        return FilenameUtils.concat(path, newFileName);
    }


    /**
     * 根据文件名称生成新的文件
     *
     * @param savePath 根据文件名称生成新的文件
     * @param fileName 文件名
     * @return 新的文件路径
     */
    public static String getByNameUuid(String savePath, String fileName) {
        String guid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        return getByName(savePath, fileName, guid);
    }

    /**
     * 根据文件名称生成新的文件
     *
     * @param savePath 要保存的文件路径
     * @param fileName 文件名
     * @param str      新文件名是原文件名后加 str
     * @return 新的文件路径
     */
    public static String getByName(String savePath, String fileName, String str) {
        String filenameWithoutExt = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        String newFileName = filenameWithoutExt + "_" + str + "." + extension;
        return FilenameUtils.concat(savePath, newFileName);
    }
}
