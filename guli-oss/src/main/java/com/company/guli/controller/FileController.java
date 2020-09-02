package com.company.guli.controller;

import com.company.guli.result.AppResponse;
import com.company.guli.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags="阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public AppResponse upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {


        String uploadUrl = fileService.upload(file);
        //返回r对象
        return AppResponse.ok(uploadUrl);
    }

}
