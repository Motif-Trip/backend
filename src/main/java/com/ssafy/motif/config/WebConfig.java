package com.ssafy.motif.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// 실제 프사 저장 경로. 로컬
	@Value("${spring.servlet.multipart.location}")
	private String resources;
	
	// upload=resources 간주
	@Value("${upload.profile.path}")
	private String upload;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(upload)
				.addResourceLocations("file:///" + resources);
	}
	
}
