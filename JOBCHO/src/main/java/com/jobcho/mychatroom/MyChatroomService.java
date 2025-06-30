package com.jobcho.mychatroom;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.user.Users;
import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyChatroomService {
	
	private final MyChatroomRepository myChatroomRepository;
	
	// 🌿 나와의 채팅방 생성
	public void createMyChatroom(Users user, Workspaces workspace) {
		MyChatroom myChatroom = new MyChatroom();
		myChatroom.setUser(user);
		myChatroom.setWorkspace(workspace);
		
		this.myChatroomRepository.save(myChatroom);
		System.out.println("<<< MyChatroomService.createMyChatroom 호출 >>> : 워크스페이스 생성 시 나만의 채팅방 자동 생성");
	}
	
	// 🌿 내 나와의 채팅방 찾기
	public MyChatroom findMychatByUserID(int userId) {
	    Optional<MyChatroom> mychat = myChatroomRepository.findByUser_UserId(userId);
	    if (mychat.isPresent()) {
	    	System.out.println("findMychatByUserIDc");
			return mychat.get();
		}else {
			return mychat.orElse(new MyChatroom());
		}
	}
}
