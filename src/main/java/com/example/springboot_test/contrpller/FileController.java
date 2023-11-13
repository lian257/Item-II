package com.example.springboot_test.contrpller;
import cn.hutool.core.io.FileUtil;
import com.example.springboot_test.common.AuthAccess;
import com.example.springboot_test.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 功能：
 * 作者：
 * 日期：2023/11/10 17:35
 */
@RestController
@RequestMapping("/file")
public class FileController {
    //单个文件的上传
    @Value("${ip: localhost}")
    String ip;

    @Value("${server.port}")
    String port;
    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";



    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException{
        //获取文件的原始名称，包含了文件名和文件类型
        String originalFilename = file.getOriginalFilename();
        String mainName = FileUtil.mainName(originalFilename);
        //获取文件后缀
        String extName = FileUtil.extName(originalFilename);
        // 判断文件的父级是否存在，如果当前文件的父级目录不存在，就创建
        if(!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);
        }
        //判断 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)){
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        //存储文件到本地的磁盘里面
        File saveFile = new File(ROOT_PATH + File.separator +originalFilename);
        file.transferTo(saveFile);
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        //返回文件的链接，这个链接就是文件的下载地址，这个下载地址是后台提供出来的
        return Result.success(url);
    }
    //单个文件的下载
    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        String filePath = ROOT_PATH  + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }
}
