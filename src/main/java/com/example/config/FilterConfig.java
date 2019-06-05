package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Tengfei.Wang
 * @Description;
 * @Date: Created in 下午6:09 19/6/18
 * @Modified by:
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean parameterFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ParameterFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ParameterFilter");
        registration.setOrder(1);
        return registration;
    }


}
