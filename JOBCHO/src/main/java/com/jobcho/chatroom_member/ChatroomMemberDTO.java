package com.jobcho.chatroom_member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatroomMemberDTO {
	private Integer chatroomMemberId;
	private Integer userId;
	private String userName;

	public ChatroomMemberDTO(ChatroomMember member) {
		this.chatroomMemberId = member.getChatroomMemberId();
		this.userId = member.getUser().getUserId();
		this.userName = member.getUser().getUserName();
	}
}
