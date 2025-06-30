package com.jobcho.mention;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.message.Messages;
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

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mentions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mention")
	@SequenceGenerator(name = "seq_mention", sequenceName = "SEQ_MENTION", allocationSize = 1)
	private Integer mentionId;
	
	@ManyToOne
	@JoinColumn(name = "chatroom_id")
	private Chatrooms chatroom;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private Users sender;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private Users receiver;
	
	@Column(name = "created_date", insertable = false)
	private LocalDateTime createdDate;
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private Messages message;

}
