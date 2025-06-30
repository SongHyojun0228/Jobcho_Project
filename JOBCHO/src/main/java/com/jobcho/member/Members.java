package com.jobcho.member;

import com.jobcho.user.Users;
import com.jobcho.workspace.Workspaces;

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
public class Members {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
	@SequenceGenerator(name = "seq_member", sequenceName = "SEQ_MEMBER", allocationSize = 1)
	private Integer memberId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne
	@JoinColumn(name = "workspace_id")
	private Workspaces workspace;
	
}
