package com.jobcho.message;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jobcho.websocket.ChatMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MymessageService {
	private final MymessageRepository mymessageRepository;

	// 🌿 나와의 채팅방 생성 메서드
	public void create(ChatMessage msg) {
		Mymessage m = new Mymessage();
		m.setMyChatroomId(msg.getChatroomId());
		m.setUserId(msg.getSenderId());
		m.setContent(msg.getContent());
		m.setIsEdited(0);
		m.setIsDeleted(0);
		this.mymessageRepository.save(m);
	}

	// 🌿 나와의 채팅방 메세지 불러오기 메서드
	public List<Mymessage> getMessage(int chatroomId) {
		List<Mymessage> messages = mymessageRepository.findByMyChatroomId(chatroomId);
		return messages;
	}
}
