package com.jobcho.message;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
public class Mymessage {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mymessage")
	@SequenceGenerator(name = "seq_mymessage", sequenceName = "SEQ_MYMESSAGE", allocationSize = 1)
	private Integer mymessageId;
	
	@Column
	private Integer myChatroomId;
	
	@Column
	private Integer userId;
	
	@Column
	private String content;
	
	@Column(name = "created_date", insertable = false)
	private LocalDateTime createdDate;
	
	@Column
	private Integer isEdited;
	
	@Column
	private Integer isDeleted;
}
