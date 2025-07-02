package com.jobcho.git;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jobcho.user.UserService;
import com.jobcho.user.Users;
import com.jobcho.workspace.WorkspaceService;
import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProjectManageController {

	private final BranchService branchService;
	private final UserService userService;
	private final WorkspaceService workspaceSerivce;

	@GetMapping("workspace/{workspaceId}/project")
	public String projectMain(@PathVariable("workspaceId") int workspaceId, Model model, Principal principal) {

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
//		List<Branch> branch = branchService.getBranchwithWorkspaceId(workspaceId);
		List<Branch> branch = branchService.getBranchwithWorkspaceIdwithCommit(workspaceId);
		Workspaces workspace = workspaceSerivce.getWorkspaceByWorkspaceId(workspaceId);

		model.addAttribute("workspace", workspace);
		model.addAttribute("branchs", branch);
		model.addAttribute(workspaceId);

		return "git/project_manage";
	}
}
