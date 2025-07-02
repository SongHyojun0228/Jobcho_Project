package com.jobcho.mychatroom;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.user.Users;
import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyChatroomService {

	private final MyChatroomRepository myChatroomRepository;
	private final ChatroomRepository chatroomRepository;

	// 🌿 나와의 채팅방 생성
	public void createMyChatroom(Users user, Workspaces workspace) {
		Chatrooms c = new Chatrooms();
		c.setCreatedBy(user.getUserId());
		c.setChatroomName("나와의 채팅");
		c.setDescription("나만의 채팅방");
		c.setIsPrivate(1);
		Chatrooms chat = this.chatroomRepository.save(c);

		MyChatroom myChatroom = new MyChatroom();
		myChatroom.setUser(user);
		myChatroom.setWorkspace(workspace);
		myChatroom.setChatroom(chat);
		this.myChatroomRepository.save(myChatroom);

		System.out.println("<<< MyChatroomService.createMyChatroom 호출 >>> : 워크스페이스 생성 시 나만의 채팅방 자동 생성");

	}

	// 🌿 내 나와의 채팅방 찾기
	public Optional<MyChatroom> findMychatByUserID(int userId) {
		return myChatroomRepository.findByUser_UserId(userId);
	}
}
