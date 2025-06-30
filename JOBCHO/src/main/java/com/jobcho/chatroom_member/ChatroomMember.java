package com.jobcho.chatroom_member;

import com.jobcho.chatroom.Chatrooms;
import com.jobcho.user.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ChatroomMember {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chatroom_memeber")
	@SequenceGenerator(name = "seq_chatroom_memeber", sequenceName = "SEQ_CHATROOM_MEMBER", allocationSize = 1)
	private Integer chatroomMemberId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	@ManyToOne
	@JoinColumn(name = "chatroom_id")
	private Chatrooms chatroom;

}
