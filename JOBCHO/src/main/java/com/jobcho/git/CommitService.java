package com.jobcho.git;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.user.Users;
import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommitService {

	private final CommitRepository commitRepository;

	Commit uploadCommit(Branch branch, String commitContent, Users user) {
		Commit commit = new Commit();

		commit.setBranch(branch);
		commit.setContent(commitContent);
		commit.setUser(user);

		try {
			this.commitRepository.save(commit);
			System.out.println("🌿 커밋 업로드 성공");
			return commit;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("🌿 커밋 업로드 실패");
			return null;
		}
	}

	Commit getLastCommit(Workspaces workspace) {
		Optional<Commit> lastCommit = this.commitRepository.findTopByBranch_WorkspaceOrderByUploadedDateDesc(workspace);
		if (lastCommit.isPresent()) {
			return lastCommit.get();
		}

		return null;
	}

}
