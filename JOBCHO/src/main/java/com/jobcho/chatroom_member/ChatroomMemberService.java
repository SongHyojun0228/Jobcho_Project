package com.jobcho.chatroom_member;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.user.UserRepository;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatroomMemberService {

	private final UserRepository userRepository;
	private final ChatroomMemberRepository chatroomMemberRepository;
	private final ChatroomRepository chatroomRepository;

	// 🌿 채팅방 멤버 추가하기 메서드
	public void addMember(Integer chatroomId, String email) {
		Optional<Chatrooms> _chatroom = this.chatroomRepository.findById(chatroomId);
		Chatrooms chatroom = _chatroom.get();
		Optional<Users> _user = this.userRepository.findByUserEmail(email);
		Users user = _user.get();

		if (!chatroomMemberRepository.existsByChatroomAndUser(chatroom, user)) {
			System.out.println("채팅 멤버 추가 : " + user.getUserEmail() + ", " + user.getUserName());

			ChatroomMember chatroomMember = new ChatroomMember();
			chatroomMember.setChatroom(chatroom);
			chatroomMember.setUser(user);
			this.chatroomMemberRepository.save(chatroomMember);
		} else {
			System.out.println("해당 유저는 해당 채팅방에 이미 존재");
		}
	}

	// 🌿 채팅방 멤버 추가하기 메서드_채팅방 생성용
	public void addMemberChatroom(Integer chatroomId, Integer userId) {
		Optional<Chatrooms> _chatroom = this.chatroomRepository.findById(chatroomId);
		Chatrooms chatroom = _chatroom.get();
		Optional<Users> _user = this.userRepository.findById(userId);
		Users user = _user.get();

		if (!chatroomMemberRepository.existsByChatroomAndUser(chatroom, user)) {
			System.out.println("채팅 멤버 추가 : " + user.getUserId() + ", " + user.getUserName());
			ChatroomMember chatroomMember = new ChatroomMember();
			chatroomMember.setChatroom(chatroom);
			chatroomMember.setUser(user);
			this.chatroomMemberRepository.save(chatroomMember);
		} else {
			System.out.println("해당 유저는 해당 채팅방에 이미 존재");
		}
	}

	// 🌿 채팅방아이디로 해당 채팅방 멤버 불러오기 메서드
	public List<ChatroomMember> getChatroomMembersByChatroomId(Integer id) {
		System.out.println("Chatroom ID: " + id);
		List<ChatroomMember> members = this.chatroomMemberRepository.findByChatroom_ChatroomIdAndUserIsNotNull(id);
		System.out.println("Members found: " + members.size());
		return members;
	}
	
	// 🌿 채팅방아이디로 해당 채팅방 멤버 불러오기 메서드
	public List<Chatrooms> getChatroomByUserId(Integer userId) {
		return this.chatroomMemberRepository.findChatroomsByUserId(userId);
	}
	
	// 🌿 해당 워크스페이스에 해당하는 유저 찾기 메서드
	public List<Users> findUsersByChatroomId(Integer workspaceId) {
		return chatroomMemberRepository.findUsersByWorkspaceId(workspaceId);
	}

	// 🌿 채팅방 나가기 (그 유저 1명만 null로)
	public void leaveChatroom(Integer chatroomId, Integer userId) {
		chatroomMemberRepository.nullifyUserFromChatroom(chatroomId, userId);
	}

	// 🌿 채팅방 삭제 (전체 유저 null로)
	public void deleteChatroomMembers(Integer chatroomId) {
		chatroomMemberRepository.nullifyAllUsersFromChatroom(chatroomId);
	}

}
