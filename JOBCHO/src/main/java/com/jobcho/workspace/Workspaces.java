package com.jobcho.workspace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jobcho.member.Members;
import com.jobcho.user.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Workspaces {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_workspace")
	@SequenceGenerator(name = "seq_workspace", sequenceName = "SEQ_WORKSPACE", allocationSize = 1)
	private Integer workspaceId;

	@Column
	private String workspaceName;

	@Column(name = "created_date", insertable = false)
	private LocalDateTime createdDate;

	@Column
	private String workspaceImg;

	@Column(unique = true)
	private String workspaceDomain;
	
	@OneToMany(mappedBy = "workspace")
    private List<Members> members = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "workspace_owner_id")  
	private Users owner;
	
}
