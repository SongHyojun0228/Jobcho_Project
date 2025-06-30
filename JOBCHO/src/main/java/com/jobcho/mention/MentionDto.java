package com.jobcho.mention;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentionDto {
	private Integer mentionId;
	private Integer chatroomId;
	private Integer senderId;
	private Integer receiverId;
	private LocalDateTime createdDate;
	private Integer workspaceId;
	private Integer messageId;
}
