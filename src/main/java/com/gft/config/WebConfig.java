package com.gft.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by iozi on 06/10/2015.
 */

@Configuration



public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
                .addResourceLocations("/public/");
    }
    @Bean
    
    ServletRegistrationBean h2servletRegistration(){

        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());

        registrationBean.addUrlMappings("/console/*");

        return registrationBean;

    }
    

}
