package com.jobcho;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${file.upload-dir}")
	private String uploadDir;

	@Value("${file.file-upload-dir}")
	private String uploadDir2;

	@Value("${file.git-upload-dir}")
	private String uploadDir3;

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		String uploadPath = new File(uploadDir).getAbsolutePath();
//		registry.addResourceHandler("/uploads/profileImg/**").addResourceLocations("file:" + uploadPath + "/");
//
//		System.out.println("파일 저장 경로(절대경로): " + uploadPath);
//	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 프로필 이미지
		String profileUploadPath = new File(uploadDir).getAbsolutePath();
		registry.addResourceHandler("/uploads/profileImg/**").addResourceLocations("file:" + profileUploadPath + "/");

		// 일반 파일
		String fileUploadPath = new File(uploadDir2).getAbsolutePath();
		registry.addResourceHandler("/uploads/files/**").addResourceLocations("file:" + fileUploadPath + "/");

		// git f(order)ile
		String gitUploadPath = new File(uploadDir3).getAbsolutePath();
        registry.addResourceHandler("/uploads/git/**")
                .addResourceLocations("file:" + gitUploadPath + "/");

        System.out.println("프로필 저장 경로: " + profileUploadPath);
        System.out.println("파일 저장 경로: " + fileUploadPath);
        System.out.println("깃 파일 저장 경로: " + gitUploadPath);

		System.out.println("프로필 저장 경로: " + profileUploadPath);
		System.out.println("파일 저장 경로: " + fileUploadPath);
		System.out.println("깃 파일 저장 경로 : " + gitUploadPath);

	}
}
