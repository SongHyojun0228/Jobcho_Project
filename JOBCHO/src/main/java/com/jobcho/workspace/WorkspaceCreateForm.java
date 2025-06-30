package com.jobcho.workspace;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceCreateForm {
	
	@NotEmpty(message = "팀 이름은 필수 항목입니다.")
	private String workspaceName;
	
	@NotEmpty(message = "팀 도메인 필수 항목입니다.")
	private String workspaceDomain;
	
}
