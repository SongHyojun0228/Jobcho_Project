package com.jobcho.cs;

import com.jobcho.user.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CsChatroom {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cschatroom")
	@SequenceGenerator(name = "seq_cschatroom", sequenceName = "SEQ_CSCHATROOM", allocationSize = 1)
	private Integer csChatroomId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private Users user;
}
