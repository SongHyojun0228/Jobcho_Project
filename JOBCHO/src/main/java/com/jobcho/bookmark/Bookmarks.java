package com.jobcho.bookmark;

import com.jobcho.chatroom.Chatrooms;
import com.jobcho.message.Messages;
import com.jobcho.mychatroom.MyChatroom;
import com.jobcho.notification.Notifications;
import com.jobcho.task.Tasks;
import com.jobcho.user.Users;

import jakarta.persistence.Column;
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
public class Bookmarks {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bookmark")
	@SequenceGenerator(name = "seq_bookmark", sequenceName = "SEQ_BOOKMARK", allocationSize = 1)
	private Integer bookmarkId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne
	@JoinColumn(name = "message_id")
	private Messages message;

	@ManyToOne
	@JoinColumn(name = "notification_id")
	private Notifications notification;

	@Column
	private Integer voteId;

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Tasks task;

	@ManyToOne
	@JoinColumn(name = "chatroom_id")
	private Chatrooms chatroom;

	@Column
	private Integer fileId;

	@ManyToOne
	@JoinColumn(name = "my_chatroom_id")
	private MyChatroom myChatroom;

	
	
	public Integer getChatroomId() {
		return (chatroom != null) ? chatroom.getChatroomId() : null;
	}

	public Integer getMyChatroomId() {
		return (myChatroom != null) ? myChatroom.getMyChatroomId() : null;
	}

	public Integer getMessageId() {
		return (message != null) ? message.getMessageId() : null;
	}

}
