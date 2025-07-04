package com.jobcho.git;

import java.util.ArrayList;
import java.util.List;

import com.jobcho.user.Users;
import com.jobcho.workspace.Workspaces;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_branch")
	@SequenceGenerator(name = "seq_branch", sequenceName = "SEQ_BRANCH", allocationSize = 1)
	private Integer branchId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "workspace_id")
	private Workspaces workspace;
	
	@Column
	private String title;
	
	@Column
	private String color;
	
	@Column
	private Integer countOfCommits;
	
	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Commit> commit = new ArrayList<>();
	
	@Column
	private Integer isMain;
	
}
