package com.jobcho.task;

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
public class Tasks {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_task")
	@SequenceGenerator(name = "seq_task", sequenceName = "SEQ_TASK", allocationSize = 1)
	private Integer taskId;

	@ManyToOne
	@JoinColumn(name = "author_id") 
	private Users author; 

	@ManyToOne
	@JoinColumn(name = "chatroom_id")
	private Chatrooms chatroom; 

	@Column
	private String taskTitle;

	@Column
	private String description;

	@Column
	private Integer status;

	@Column(name = "created_date", insertable = false)
	private LocalDateTime createdDate;

	@Column
	private LocalDateTime startDate;

	@Column
	private LocalDateTime endDate;

	@Column
	private Integer mychatroomId;
}

