package com.jobcho.mychatroom;

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

@Entity
@Getter
@Setter
public class MyChatroom {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mychatroom")
	@SequenceGenerator(name = "seq_mychatroom", sequenceName = "SEQ_MYCHATROOM", allocationSize = 1)
	private Integer myChatroomId;

	@ManyToOne
    @JoinColumn(name = "user_id")
	private Users user;
	
	@ManyToOne
    @JoinColumn(name = "workspace_id")	
	private Workspaces workspace;
	
}
