package com.jobcho.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {

	private MimeMessage number; // 이메일 인증 숫자를 저장하는 변수

	private final MailService mailService;

	// 🌿 인증 이메일 전송 POST
	@PostMapping("/mailSend")
	@ResponseBody
	public Map<String, Object> mailSend(@RequestParam("mail") String mail) {
		Map<String, Object> map = new HashMap<>();

		try {
			mailService.sendMail(mail);

			int verificationCode = mailService.getVerificationNumber(mail);

			map.put("success", true);
			map.put("number", verificationCode);
		} catch (Exception e) {
			map.put("success", false);
			map.put("error", e.getMessage());
		}

		return map;
	}

}
