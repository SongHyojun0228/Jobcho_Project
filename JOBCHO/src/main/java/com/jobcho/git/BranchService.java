package com.jobcho.git;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.user.Users;
import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {

	private final BranchRepository branchRepository;
	
	Branch getBranchByBranchId(Integer branchId) {
		return this.branchRepository.findById(branchId).get();
	}

	public void createBranch(String branchName, Users user, Workspaces worksapce, String rgb, Integer countOfCommits, Integer isMain) {
		Branch branch = new Branch();

		branch.setTitle(branchName);
		branch.setUser(user);
		branch.setWorkspace(worksapce);
		branch.setColor(rgb);
		branch.setCountOfCommits(countOfCommits);
		branch.setIsMain(isMain);

		try {
			this.branchRepository.save(branch);
			System.out.println("🌿 브랜치 생성 완료");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("🌿 브랜치 생성 실패");
		}
	}

	List<Branch> getByUserIdAndWorkspaceId(Integer userId, Integer workspaceId) {
		return this.branchRepository.findByUserUserIdAndWorkspaceWorkspaceId(userId, workspaceId);
	}

	// 🌿 워크스페이스 id 로 브랜치 탐색
	public List<Branch> getBranchwithWorkspaceId(Integer workspaceId) {
		return this.branchRepository.findByWorkspace_WorkspaceId(workspaceId);
	}
	
	// 🌿 워크스페이스 내 메인 브랜치 탐색
	public Branch getMainBranchByWorkspaceId(Integer workspaceId) {
		Optional<Branch> mainBranch = this.branchRepository.findBranchByWorkspaceIdAndTitle(workspaceId, "main");
		return mainBranch.get();
	}

	public List<Branch> getBranchwithWorkspaceIdwithCommit(Integer workspaceId) {
		return this.branchRepository.findWithCommitsByWorkspaceId(workspaceId);
	}

}
