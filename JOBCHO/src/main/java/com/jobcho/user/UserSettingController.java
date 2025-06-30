package com.jobcho.user;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserSettingController {

	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// 🌿 계정설정 페이지 GET
	@GetMapping("/setting")
	public String getUserSetting(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/user/login";
		}

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);

		return "user/user_setting";
	}

	@Value("${file.upload-dir}")
	private String uploadDir;

	// 🌿 프로필 이미지 변경 POST
	@PostMapping("/upload/profile")
	public String handleFileUpload(@RequestParam("user_img") MultipartFile file, Model model, Principal principal)
			throws IOException {
		if (file.isEmpty()) {
			return "업로드할 파일이 없습니다.";
		}

		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

		File directory = new File(uploadDir).getAbsoluteFile();
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File dest = new File(directory, fileName);
		file.transferTo(dest);

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		try {
			this.userService.uploadUserProfile(user, fileName);
		} catch (Exception e) {
			System.out.println("프로필 변경 중 실패");
			e.printStackTrace();
		}

		System.out.println("프로필 변경 성공");
		return "redirect:/user/setting";
	}

	// 🌿 유저 이름 변경 POST
	@PostMapping("/username/change")
	public String changeUserName(@RequestParam("new_user_name") String newUserName, Principal principal,
			CsrfToken token) {
		System.out.println("클라이언트가 보낸 CSRF 토큰: " + token.getToken());
		System.out.println("POST 요청 수신: " + newUserName);
		if (principal == null) {
			return "redirect:/user/login";
		}

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();

		try {
			this.userService.changeUserName(user, newUserName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/user/setting";
	}

	// 🌿 유저 이메일 변경 POST
	@PostMapping("/useremail/change")
	public String changeUserEmail(@RequestParam("new_user_email") String newUserEmail, Principal principal, Model model,
			RedirectAttributes redirectAttributes) {

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);

		if (userService.existsByEmail(newUserEmail)) {
			model.addAttribute("alert_msg", "이미 사용 중인 이메일입니다.");
			model.addAttribute("user", user);
			return "user/user_setting";
		}

		try {
			this.userService.changeUserEmail(user, newUserEmail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("이메일 변경 중 예외 발생");
			model.addAttribute("alert_msg", "이메일 변경 도중 문제가 발생했습니다. 다시 시도해주세요.");
			model.addAttribute("user", user);
		}

		System.out.println("이메일 변경 성공");
		return "redirect:/user/logout";
	}

	// 🌿 유저 비밀번호 변경 POST
	@PostMapping("/userpassword/change")
	public String changePassword(@RequestParam("user_password") String userPassword,
			@RequestParam("new_user_password") String newUerPassword, Principal principal, Model model) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();

		System.out.println("\n" + user.getUserEmail());

		if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
			System.out.println("비밀번호 변경 중 : 현재 비밀번호 틀림");
			model.addAttribute("alert_msg", "현재 비밀번호와 일치하지 않습니다.");
			model.addAttribute("user", user);
			return "user/user_setting";
		}

		try {
			this.userService.changePassword(user, newUerPassword);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("비밀번호 변경 중 예외 발생");
			model.addAttribute("alert_msg", "비밀번호 변경 중 예외 발생");
			model.addAttribute("user", user);
			return "user/user_setting";
		}

		System.out.println("비밀번호 변경 성공");
		return "redirect:/user/logout";
	}

	// 🌿 계정 삭제  POST
	@PostMapping("/account/delete")
	public String deleteUser(Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		this.userService.deleteUser(user);

		return "redirect:/user/logout";
	}

}
