package com.jobcho.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jobcho.member.InviteTokenService;
import com.jobcho.user.UserService;
import com.jobcho.user.Users;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {

	private final JavaMailSender javaMailSender;
	private static final String senderEmail = "thdgywns2300@gmail.com";
	private final Map<String, Integer> emailVerificationMap = new HashMap<>();
	private final UserService userService;
	private final InviteTokenService inviteTokenService;

	// 🌿 비밀번호 변경 메일 전송 메서드
	@Async
	public void sendMail(String mail) {
		int number = createNumber();
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, mail);
			message.setSubject("이메일 인증");
			String body = "<h3>임시 비밀번호입니다.</h3><h1>" + number + "</h1><h3>필요시, 로그인 후 비밀번호 변경을 해주세요.</h3>";
			message.setText(body, "UTF-8", "html");

			javaMailSender.send(message);

			emailVerificationMap.put(mail, number);

			Optional<Users> _user = this.userService.getUserByEmail(mail);
			Users user = _user.get();
			this.userService.changePassword(user, String.valueOf(number));

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// 🌿 인증번호 ( 임시 비밀번호 ) 불러오기 메서드
	public int getVerificationNumber(String mail) {
		return emailVerificationMap.getOrDefault(mail, -1);
	}

	// 🌿 인증번호 ( 임시 비밀번호 ) 생성 메서드
	private int createNumber() {
		return (int) (Math.random() * (900000000)) + 1000000000;
	}

	// 🌿 인증번호 ( 임시 비밀번호 ) 검증 메서드
	public boolean checkVerificationNumber(String mail, int userNumber) {
		int storedNumber = getVerificationNumber(mail);
		return storedNumber == userNumber;
	}

	// 🌿 워크스페이스 초대 이메일 전송 메서드
	@Async
	public void sendInviteMail(String inviteEmail, Integer workspaceId, String workspaceName) {
		String token = inviteTokenService.createInviteToken(workspaceId, inviteEmail);
		String inviteUrl = "http://localhost:2003/workspace/invite/" + workspaceId + "/" + token;

		String subject = "[" + workspaceName + "] 팀 초대 메일입니다.";

		String htmlBody = """
				<html>
				<body style="font-family: Noto Sans Kr, sans-serif;">
				    <div style="max-width: 55rem; margin: 0 auto; padding: 1.5rem; background: #fff; border: 1px solid #ddd; border-radius: 8px;">
				        <h2 style="color: black; margin-top: 1rem;">🌿 %s 워크스페이스에 초대되었습니다!</h2>
				        <p style="color: rgb(125, 125, 125); font-size: 1.2rem;">아래 버튼을 눌러 초대를 수락해주세요.</p>
				        <div style="margin-bottom:1rem;">
				            <a href="%s" style="background: rgb(6, 195, 115); color: rgb(250, 250, 250); padding: 10px 20px; text-decoration: none; border-radius: 5px; fort-size:1.2rem; font-weight: 500;">
				                초대 수락하기
				            </a>
				        </div>
				    </div>
				</body>
				</html>
				"""
				.formatted(workspaceName, inviteUrl);

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, inviteEmail);
			message.setSubject(subject);
			message.setText(htmlBody, "UTF-8", "html");

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// 🌿 채팅방 초대 이메일 전송 메서드
	public void sendChatroomInviteMail(String inviteEmail, Integer chatroomId, String chatroomName) {
		String token = inviteTokenService.createInviteToken(chatroomId, inviteEmail);
		String inviteUrl = "http://localhost:2003/workspace/chatroom/invite/" + chatroomId + "/" + token;
		System.out.println("inviteUrl : " + inviteUrl);

		String subject = "[" + chatroomName + "] 채팅방 초대 메일입니다.";

		String htmlBody = """
				<html>
				<body style="font-family: Noto Sans Kr, sans-serif;">
				    <div style="max-width: 55rem; margin: 0 auto; padding: 1.5rem; background: #fff; border: 1px solid #ddd; border-radius: 8px;">
				        <h2 style="color: black; margin-top: 1rem;">🌿 %s 채팅방에 초대되었습니다!</h2>
				        <p style="color: rgb(125, 125, 125); font-size: 1.2rem;">아래 버튼을 눌러 초대를 수락해주세요.</p>
				        <div style="margin-bottom:1rem;">
				            <a href="%s" style="background: rgb(6, 195, 115); color: rgb(250, 250, 250); padding: 10px 20px; text-decoration: none; border-radius: 5px; fort-size:1.2rem; font-weight: 500;">
				                초대 수락하기
				            </a>
				        </div>
				    </div>
				</body>
				</html>
				"""
				.formatted(chatroomName, inviteUrl);

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, inviteEmail);
			message.setSubject(subject);
			message.setText(htmlBody, "UTF-8", "html");

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}