package com.sqber.commonWeb;

import com.sqber.commonTool.PathUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 * @author SQB10190
 */
public class UploadFile {

    /**
     * 创建一个 EXCEL 上传的实例（后缀必须是 xls 或者 xlsx，大小 20M 以内）
     *
     * @param filePath 文件保存路径
     * @param files    前台传递的文件
     * @return UploadFile
     */
    public static UploadFile createExcelUploadForExcel(String filePath, MultipartFile[] files) {
        return new UploadFile(filePath, files, (fileName, ext, fileSize) -> {
            if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
                throw new ValidationException(String.format("%s 类型不对。请上传 xls 或 xlsx 类型的文件", fileName));
            }
            long fileSizeM = (fileSize / 1024 / 1024);
            long sizeLimit = 20;
            if (fileSizeM > sizeLimit) {
                throw new ValidationException(String.format("%s(%sM) 文件不能大于 %sM", fileName, fileSizeM, sizeLimit));
            }
        });
    }

    /**
     * 文件验证接口
     */
    public interface Validator {
        /**
         * 验证
         *
         * @param fileName 文件名
         * @param ext      扩展名
         * @param fileSize 文件大小（单位字节）
         * @throws ValidationException 异常
         */
        void validate(String fileName, String ext, long fileSize) throws ValidationException;
    }

    private String filePath;
    private MultipartFile[] files;
    private UploadFile.Validator validator;

    /**
     * 实例化
     *
     * @param filePath 文件保存路径
     * @param files 上传的文件
     * @param validator 验证接口
     */
    public UploadFile(String filePath, MultipartFile[] files, UploadFile.Validator validator) {
        this.filePath = filePath;
        this.files = files;
        this.validator = validator;
    }

    /**
     * 保存
     *
     * @return 文件在服务器上的路径
     * @throws IOException 异常
     */
    public List<String> save() throws IOException {

        for (MultipartFile multipartFile : files) {
            validate(multipartFile);
        }

        List<String> paths = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            paths.add(save(multipartFile));
        }

        return paths;
    }

    private void validate(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();
        validator.validate(fileName, FilenameUtils.getExtension(fileName), fileSize);
    }

    private String save(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        Assert.notNull(filePath, "filePath 不能为空");
        String path = PathUtil.getByNameUuid(filePath, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(file.getBytes());
        }
        return path;
    }
}
