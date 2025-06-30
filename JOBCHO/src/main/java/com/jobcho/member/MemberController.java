package com.jobcho.member;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobcho.mail.MailService;
import com.jobcho.user.UserService;
import com.jobcho.user.Users;
import com.jobcho.workspace.WorkspaceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final InviteTokenRepository inviteTokenRepository;
	private final MemberService memberSerivce;
	private final MailService mailService;
	private final WorkspaceService workspaceService;
	private final UserService userService;

	@GetMapping("/workspace/invite/{workspaceId}/{token}")
	public String acceptInvite(@PathVariable("token") String token,
			@PathVariable("workspaceId") String workspaceId, Principal principal, Model model) {
		InviteToken inviteToken = inviteTokenRepository.findByToken(token)
				.orElseThrow(() -> new IllegalArgumentException("잘못된 초대 링크입니다."));

		if (inviteToken.getExpiredAt().isBefore(LocalDateTime.now())) {
			System.out.println("초대 링크가 만료되었습니다.");
			return "error/404";
		}

		String inviteEmail = inviteToken.getInviteEmail();
		String userEmail = principal.getName();
		
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);

		System.out.println("초대된 이메일 : " + inviteEmail + ", 로그인한 유저 이메일 : " + userEmail);

		if (!inviteEmail.equals(userEmail)) {
			System.out.println("초대된 사용자가 아닙니다.");
			return "error/404";
		}

		memberSerivce.addMember(inviteToken.getWorkspaceId(), userEmail);

		inviteTokenRepository.delete(inviteToken);

		System.out.println("초대가 수락되었습니다! 워크스페이스로 이동합니다.");
		return "redirect:/workspace/" + workspaceId;
	}

    // 🌿 이메일로 워크스페이스 초대 페이지 GET
	@GetMapping("/{workspaceId}/invite")
	public String getInviteMember(@PathVariable("workspaceId") Integer workspaceId, Model model, Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);
		model.addAttribute(workspaceId);
		return "workspace/invite_member";
	}

    // 🌿 이메일로 워크스페이스 초대 POST
	@PostMapping("/{workspaceId}/invite")
	public String inviteMember(@PathVariable("workspaceId") Integer workspaceId,
			@RequestParam("email") String email) {
		String workspaceName = workspaceService.getWorkspaceNameById(workspaceId);
		mailService.sendInviteMail(email, workspaceId, workspaceName);

	    return "redirect:/workspace/" + workspaceId;
	}

}
