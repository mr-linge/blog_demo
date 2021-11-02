package com.linyuegx.blog_demo.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Create by lin on  2021/10/26 0:22
 */
@Configuration
public class WebConfig implements  WebMvcConfigurer  {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginIntercepter())
                  .addPathPatterns("/admin/*")
                        .excludePathPatterns("/admin")
                        .excludePathPatterns("/admin/login");
        }

}
