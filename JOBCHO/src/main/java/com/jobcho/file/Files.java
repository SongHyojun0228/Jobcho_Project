package com.jobcho.file;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

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

@Getter
@Setter
@Entity
@DynamicInsert
public class Files {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_file")
	@SequenceGenerator(name = "seq_file", sequenceName = "SEQ_FILE", allocationSize = 1)
	private Integer fileId;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private Users sender;

	@ManyToOne
	@JoinColumn(name = "chatroom_id")
	private Chatrooms chatroom;

	@ManyToOne
	@JoinColumn(name = "message_id")
	private Messages message;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "uploaded_date", insertable = false, updatable = false)
	private LocalDateTime uploadedDate;
}
