package com.ccb.backend.controller;

import com.ccb.backend.result.Result;
import com.ccb.backend.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/user/common")
@Slf4j
@Tag(name = "通用相关接口", description = "通用相关接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

//    @PostMapping("/upload")
    @PostMapping(value = "upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "文件上传", description = "文件上传接口")
    public Result<String> upload(@RequestPart("file") MultipartFile file) {  //  文件类型

        log.info("文件上传：{}", file);
        try {

            String OriginalFileName = file.getOriginalFilename();
            // 截取文件后缀名
            String extension = OriginalFileName.substring(OriginalFileName.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;

            // 文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);   // getByte方法：把整个文件内容读成字节数组（内存中）
            return Result.success(filePath);

        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage());
        }
        return Result.error("文件上传失败");

    }

}
