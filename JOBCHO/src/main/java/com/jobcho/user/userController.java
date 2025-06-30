package com.jobcho.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class userController {

	private final UserService userService;

	userController(UserService userService) {
		this.userService = userService;
	}

	// 🌿 메인화면 페이지 GET
	@GetMapping("/index")
	public String index() {	
		return "index";
	}

	// 🌿 회원가입 페이지 GET
	@GetMapping("/signup")
	public String getSignup(UserCreateForm userCreateForm) {
		return "user/signup";
	}

	// 🌿 회원가입 POST
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user/signup";
		}

		try {
			userService.create(userCreateForm.getUserEmail(), userCreateForm.getUserPassword(),
					userCreateForm.getUserName());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 이메일입니다.");
			return "user/signup";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "user/signup";
		}

		return "redirect:/";
	}

	// 🌿 로그인 페이지 GET
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("title", "로그인");
		return "user/login";
	}
	
	// 🌿 비밀번호 찾기 페이지 GET
	@GetMapping("/find/password")
	public String getTest(Model model) { 
		model.addAttribute("title", "비밀번호 찾기");
		return "user/find_password";
	}
	
}
