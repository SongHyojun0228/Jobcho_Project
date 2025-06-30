package com.jobcho;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jobcho.user.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	// 🌿 로그인 성공 시 메인 화면으로 이동
	public CustomLoginSuccessHandler() {
		setDefaultTargetUrl("/");
		setAlwaysUseDefaultTargetUrl(true);
	}

	@Autowired
	private ApplicationContext applicationContext;

	// 🌿 로그인 성공 시, 이메일을 가져오고
	// 해당 이메일을 통해 유저의 "현재 활동 중 = 1" 업데이트
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (authentication != null) {
			String email = authentication.getName();
			UserService userService = applicationContext.getBean(UserService.class);
			userService.updateIsActiveByEmail(email, 1);

			System.out.println("CustomLoginSuccessHandler >> login : " + email);
			System.out.println("UserService 가져옴: " + userService);
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
