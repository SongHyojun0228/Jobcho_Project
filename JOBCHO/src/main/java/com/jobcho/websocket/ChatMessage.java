package com.jobcho.websocket;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
	private Integer senderId;
	private String sender;
	private Integer receiverId;
	private String receiver;
	
	private String content;
	private String profile;
	private String fileName;
	private LocalDateTime createdDate;
    private Integer isEdited;
    private Integer isDeleted;
    
	private Integer chatroomId;
	private List<String> mentions;
}
