package com.sqber.log4jdemo.controller;

import com.sqber.commonTool.ListUtil;
import com.sqber.commonTool.excel.CommonExcel;
import com.sqber.commonTool.excel.SaveResult;
import com.sqber.commonTool.myenum.IEnum;
import com.sqber.commonWeb.R;
import com.sqber.log4jdemo.model.SaleModel;
import com.sqber.log4jdemo.myenum.CheckInfoSyncState;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MinIOController {

    @Autowired
    private MinioClient minioClient;

    @PostMapping("/file/upload")
    public R upload(@RequestParam(name = "file", required = false) MultipartFile[] file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        if (file == null || file.length == 0) {
            return R.error("上传文件不能为空");
        }
        String bucketName = "video-";
        List<String> orgfileNameList = new ArrayList<>(file.length);
        for (MultipartFile multipartFile : file) {
            String orgfileName = multipartFile.getOriginalFilename();
            orgfileNameList.add(orgfileName);
            //文件上传
            InputStream in = multipartFile.getInputStream();
            ObjectWriteResponse resp = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(orgfileName)
                            .stream(in, multipartFile.getSize(), -1)
                            .contentType(multipartFile.getContentType())
                            .build()
            );
            in.close();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("bucketName", bucketName);
        data.put("fileName", orgfileNameList);
        return R.success(data);
    }

    @GetMapping("/file/download/{bucketName}/{fileName}")
    public void download(HttpServletResponse response,
                         @PathVariable("bucketName") String bucketName,
                         @PathVariable("fileName") String fileName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        InputStream in = null;
        // 获取对象信息
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
        response.setContentType(stat.contentType());
        response.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(fileName, "UTF-8"));
        //文件下载
        in = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
        IOUtils.copy(in, response.getOutputStream());
        in.close();
    }

    @GetMapping("/file")
    public String getFileUrl(String bucketName, String fileName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(fileName)
                .expiry(604800)
                .build();
        return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
    }
}
