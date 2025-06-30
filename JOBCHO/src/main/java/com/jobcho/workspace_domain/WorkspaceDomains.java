package com.jobcho.workspace_domain;

import com.jobcho.workspace.Workspaces;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WorkspaceDomains {
	
	@Id 
	@Column
    private Integer workspaceId;

    @OneToOne
    @JoinColumn(name = "workspace_id")
    private Workspaces workspace;

	@Column
	private String workspaceDomain;

	@Column
	private String redirectWorkspaceDomain;
	
}