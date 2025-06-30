package com.jobcho.member;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteTokenService {
	 
	private final InviteTokenRepository inviteTokenRepository;
	
    // 🌿 이메일초대 토큰 생성 메서드
	public String createInviteToken(Integer workspaceId, String inviteEmail) {
	    String token = UUID.randomUUID().toString();

	    InviteToken inviteToken = InviteToken.builder()
	        .token(token)
	        .workspaceId(workspaceId)
	        .inviteEmail(inviteEmail)
	        .expiredAt(LocalDateTime.now().plusDays(1))
	        .build();

	    inviteTokenRepository.save(inviteToken);
	    return token;
	}
	
}
