package com.jobcho.git;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitFolderService {

	private final GitFolderRepository gitFolderRepository;

	public GitFolder uploadFolder(String folderPath, Commit commit) {
		GitFolder gitFolder = new GitFolder();
		gitFolder.setFolderName(folderPath);
		gitFolder.setCommit(commit);

		try {
			this.gitFolderRepository.save(gitFolder);
			System.out.println("🌿 폴더 생성 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("🌿 폴더 생성 실패");
		}

		return gitFolder;
	}

	public boolean isExistingFolder(String folderName) {
		if (this.gitFolderRepository.findByFolderName(folderName).isPresent()) {
			return true;
		}

		return false;
	}

	public GitFolder findByPathAndCommit(String folderPath, Commit commit) {
		return gitFolderRepository.findByFolderNameAndCommit(folderPath, commit).orElse(null);
	}

//	public List<GitFolder> getByWorkspaceId(Integer workspaceId) {
//		List<GitFolder> gitFoldersByWorksapce = this.gitFolderRepository.findAllByWorkspaceId(workspaceId);
//		return gitFoldersByWorksapce;
//	}

}
