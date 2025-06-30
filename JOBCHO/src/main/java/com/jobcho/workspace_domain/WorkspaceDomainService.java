package com.jobcho.workspace_domain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceDomainService {

	private final WorkspaceDomainRepository workspaceDomainRepository;
	
	public void createWorkspaceDomain(Integer workspaceId, String workspaceDomain, String redirectWorkspaceDomain) {
		WorkspaceDomains workspaceDomainObj = new WorkspaceDomains();
		workspaceDomainObj.setWorkspaceId(workspaceId);
		workspaceDomainObj.setWorkspaceDomain(workspaceDomain);
		workspaceDomainObj.setRedirectWorkspaceDomain(redirectWorkspaceDomain);
		
		this.workspaceDomainRepository.save(workspaceDomainObj);
	}

	public String findRedirectWorkspaceDomainByWorkspaceDomain(String workspaceDomain) {
		return "1";
	}
	
}
