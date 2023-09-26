package org.example.ruiji.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.ruiji.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")

public class CommonController {
    //获取在application配置文件中的路径信息
    //这里有问题
    //类似el表达式
    /*@Value("${ruiji.path}")
    private String basePath;*/



    //在controller的方法中声明一个MultipartFile类型的参数即可接受上传的文件
    //通过浏览器进行文件下载，本质上就是服务端将文件以流的形式写会浏览器的过程
    //MultipartFile的参数名，必须和发送请求时的 name值保持一致
    //file是个临时文件，需要转存到指定位置，否则本次请求完成后临时文件就会删除
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {

        //将临时文件转存到指定位置
//            file.transferTo(new File( "C:\\Users\\Administrator\\Desktop\\zzz.jpg"));
        String originalFilename = file.getOriginalFilename();//获取上传文件的名字

        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名重复造成文件覆盖
        String filename = UUID.randomUUID().toString() + substring;

     /*   //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //如果没有则进行创建
            dir.mkdirs();
        }*/
        try {
            file.transferTo(new File( "C:\\Users\\Administrator\\Desktop\\"+filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读出文件内容
            FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\" + name));
            //输入流，通过输入流将文件写会浏览器，在浏览器回显上传的图片
            ServletOutputStream outputStream = response.getOutputStream();
            //使用IOUtils实现对拷
            IOUtils.copy(fileInputStream,outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
