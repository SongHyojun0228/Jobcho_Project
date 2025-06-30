package com.jobcho.notification;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jobcho.chatroom.Chatrooms;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notification")
	@SequenceGenerator(name = "seq_notification", sequenceName = "SEQ_NOTIFICATION", allocationSize = 1)
	private Integer notificationId;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private Users author;

	@ManyToOne
	@JoinColumn(name = "chatroom_id")
	private Chatrooms chatroom;

	@Column(nullable = false)
	private String content;

	@Column(name = "created_date", insertable = false, updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "is_edited")
	private Integer isEdited;

	@Column(name = "is_deleted")
	private Integer isDeleted;

	@Column(name = "title")
	private String title;
}
