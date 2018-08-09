package com;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



public class Application {
	@Bean
    public WebMvcConfigurerAdapter corsConfigurer() {
		
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
               /* registry.addMapping("/getEmployees").allowedOrigins("http://localhost:4200");
                registry.addMapping("/").allowedOrigins("http://localhost:4200");
               */ 
               registry.addMapping("/rem/**")
      	   	  .allowedOrigins("http://localhost:4200")
      		  .allowedMethods("POST", "GET",  "PUT", "OPTIONS", "DELETE")
      		  .allowedHeaders("Authorization", "Content-Type")
      		  .allowCredentials(false)
      		  .maxAge(4800);
            }
        };
    }

}
