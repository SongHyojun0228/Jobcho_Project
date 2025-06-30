package com.jobcho;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorController {
	
	public String getErrorPage(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {
			int statusCode = Integer.parseInt(status.toString());
			
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error/404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error/500";
			}
		}
		
		return "error/404";
	}
	
}
