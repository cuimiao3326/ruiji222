package org.example.ruiji;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
//为了让过滤器生效（被扫描到）,因为filter是WEB组件，而不是SpringBoot的内部组件
@ServletComponentScan
//开启事务
@EnableTransactionManagement
public class RuijiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuijiApplication.class,args);
        log.info("项目启动成功");
    }
}
