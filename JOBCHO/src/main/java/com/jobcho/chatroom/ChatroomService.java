package com.jobcho.chatroom;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ChatroomService {

	private final ChatroomRepository chatroomRepository;
	
	public Chatrooms getChatroomByChatroomId(Integer chatroomId) {
		return this.chatroomRepository.findById(chatroomId).get();
	}
	
    // 🌿 채팅방(토픽) 생성 메서드
	public Integer create(int folderid, int createdby, String chatroomname, String description, int isPrivate) {
		Chatrooms c = new Chatrooms();
		c.setFolderId(folderid);
		c.setCreatedBy(createdby);
		c.setChatroomName(chatroomname);
		c.setDescription(description);
		c.setIsPrivate(isPrivate);
		
		Chatrooms chat = this.chatroomRepository.save(c);
		return chat.getChatroomId();
		
	}

    // 🌿 채팅방아이디로 채팅방 이름 불러오기 메서드
	public String getChatroomNameById(Integer chatroomId) {
		Chatrooms chatroom = this.chatroomRepository.getById(chatroomId);
		return chatroom.getChatroomName();
	}
	
    // 🌿 채팅방 정보 수정하기 메서드 ( 토픽 정보 수정하기 )	
	public void updateChatroom(int chatroomId, String newName,String newContent) {
	    Optional<Chatrooms> optionalchatroom = chatroomRepository.findById(chatroomId);

	    if (optionalchatroom.isPresent()) {
	        Chatrooms chatroom = optionalchatroom.get();
	        chatroom.setDescription(newContent);
	        chatroom.setChatroomName(newName); 
	        chatroomRepository.save(chatroom);
	    }
	}
	
}

