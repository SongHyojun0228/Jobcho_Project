package com.jobcho.cs;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Transactional
public class CsChatMessage {
	private String sender;
	private Integer senderId;
	private String senderImg;
	private String receiver;
	private Integer receiverId;
	private String content;
	private Integer csChatroomId;
}
