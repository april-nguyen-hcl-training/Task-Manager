package com.hcl.taskmanagerwebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/home").setViewName("thymeleaf/home");
    registry.addViewController("/").setViewName("thymeleaf/home");
    registry.addViewController("/login").setViewName("thymeleaf/login");
  }
}
