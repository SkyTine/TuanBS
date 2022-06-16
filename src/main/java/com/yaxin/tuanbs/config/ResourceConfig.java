package com.yaxin.tuanbs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Yaxin
 * @date 2022/6/12 11:08
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    //服务器静态文件夹配置
    @Value("${static.profile}")
    private String profile;

    //配置静态文件目录路由
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file:"+profile+"/");
    }
}
