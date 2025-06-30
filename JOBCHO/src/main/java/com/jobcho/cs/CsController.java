package com.jobcho.cs;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobcho.message.Messages;
import com.jobcho.user.UserService;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CsController {

	private final UserService userService;
	private final CSService csService;

    // 🌿 고객센터 페이지 GET
	@GetMapping("/cs")
	public String getCs(Principal principal, Model model) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();

		model.addAttribute("user", user);

		return "cs/customerservicecenter";
	}

    // 🌿 1:1 고객센터 채팅방 GET
	@GetMapping("/cs/chat/{csChatroomId}")
	public String getCsChat(@PathVariable("csChatroomId") Integer csChatroomId,  Principal principal, Model model) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		
	    this.csService.markMessagesAsRead(csChatroomId, user.getUserId());

		List<CS> messages = this.csService.getCsMessage(csChatroomId);

		model.addAttribute("user", user);
		model.addAttribute("messages", messages);
		model.addAttribute("csChatroomId", csChatroomId);
			
		return "cs/customerservicetalk";
	}

    // 🌿 1:1 고객센터 채팅방 메세지 전송 POST
	@MessageMapping("/chat.sendCsMessage")
	public void sendCsMessage(CsChatMessage csMessage) {
		try {
			this.csService.create(csMessage);
			System.out.println("🌿 csMessage.getCsChatroomId() : " + csMessage.getCsChatroomId());

			messagingTemplate.convertAndSend("/cs/chat/" + csMessage.getCsChatroomId(), csMessage);

			System.out.println("🌿 CsController.sendCsMessage 호출");
		} catch (Exception e) {
			System.out.println("❌ sendCsMessage 예외 발생");
			e.printStackTrace();
		}
	}
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
}
