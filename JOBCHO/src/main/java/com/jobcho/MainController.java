package com.jobcho;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jobcho.user.UserService;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final UserService userService;

	@GetMapping("/")
	public String redirectToSignup(Model model, Principal principal) {

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);

		return "redirect:/index";
	}

	@GetMapping("/error/404")
	public String get404Page(Model model, Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);
		return "error/404";
	}

	@GetMapping("/error/500")
	public String get500Page(Model model, Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);
		return "error/500";
	}

}
