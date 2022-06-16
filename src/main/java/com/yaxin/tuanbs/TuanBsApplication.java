package com.yaxin.tuanbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.yaxin.tuanbs.mapper")
@SpringBootApplication
public class TuanBsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuanBsApplication.class, args);
    }

}
