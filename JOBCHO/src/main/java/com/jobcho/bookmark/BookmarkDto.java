package com.jobcho.bookmark;

import com.jobcho.user.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkDto {
	private Integer bookmarkId;
	
	private Users user;
	
	private Integer messageId;
	
	private Integer notificationId;
	
	private Integer voteId;
	
	private Integer taskId;
	
	private Integer chatroomId;
	
	private Integer fileId;
	
	private Integer myChatroomId;
}
