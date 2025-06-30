package com.jobcho.cs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CsChatroomService {

	private final CsChatroomRepository csChatroomRepository;

	// 🌿 1 : 1 고객센터 채팅방 생성 메서드
	public void createCsChatRoom(Users user) {
		CsChatroom csChatroom = new CsChatroom();
		csChatroom.setUser(user);

		this.csChatroomRepository.save(csChatroom);
	}

	// 🌿 모든 1 : 1 고객센터 채팅방 찾기
	public List<CS> findAllCsChatroom() {
		return this.csChatroomRepository.findByCsChatroomSentDateOrdered();
	}

	// 🌿 읽지 않은 채팅 개수 구하는 메서드
	public List<Object[]> countUnreadByChatroom(Integer userId) {
		return csChatroomRepository.countUnreadByChatroom(userId);
	}
}
