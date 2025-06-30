package com.jobcho;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.jobcho.user.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private ApplicationContext applicationContext;

	// 🌿 로그아웃 성공 시, 이메일을 가져오고
	// 해당 이메일을 통해 유저의 "현재 활동 중 = 0" 업데이트
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (authentication != null) {
			String email = authentication.getName();
			UserService userService = applicationContext.getBean(UserService.class);
			userService.updateIsActiveByEmail(email, 0);
		}
		response.sendRedirect("/");
	}
}
