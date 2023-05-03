package com.example.controller;

import com.example.AliOSSUtils;
import com.example.pojo.Result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
public class uploadController {

//    在AliOSSAutoCOnfiguration中创建的bean对象
    @Autowired
    private AliOSSUtils aliOSSUtils;
    //本地保存
//    @PostMapping("/upload")
//    public void upload(MultipartFile file) throws IOException {
//        file.transferTo(new File("G:\\"+file.getOriginalFilename()));
//    }

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException{
        log.info("上传图片"+image.getOriginalFilename());
//      使用aliyunosss工具来上传文件
        String url = aliOSSUtils.upload(image);
        log.info("上传完毕"+url);
        return Result.success(url);
    }
}
