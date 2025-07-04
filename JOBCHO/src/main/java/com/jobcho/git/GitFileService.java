package com.jobcho.git;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitFileService {

	@Value("${file.git-upload-dir}")
	private String uploadGitFileDir;

	private final GitFileRepository gitFileRepository;

	void uploadGitFiles(GitFolder gitFolder, Commit commit, String fileName, String relativePath) {

		GitFile gitFile = new GitFile();

		gitFile.setGitFolder(gitFolder);
		gitFile.setCommit(commit);
		gitFile.setFileName(fileName);
		gitFile.setFilePath(relativePath);

		try {
			this.gitFileRepository.save(gitFile);
			System.out.println("🌿 파일 생성 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("❌ 파일 생성 실패");
		}
	}

	List<GitFile> getAllGitFilesByWorkspaceId(Integer workspaceId) {
		return this.gitFileRepository.findAllByWorkspaceIdOrderByCommitUploadedDateDesc(workspaceId);
	}

	public Integer getLastCommitFileCount(Commit lastCommit, Workspaces workspace) {

		Integer countOfLastCommitFile = this.gitFileRepository.countByCommitAndWorkspace(lastCommit, workspace);

		return countOfLastCommitFile;
	}

	public List<GitFile> getAllGitFilesByWorkspaceIdAndBranchId(Integer workspaceId, Integer branchId) {
		return gitFileRepository.findByWorkspaceIdAndBranchIdOrderByCommitUploadedDateDesc(workspaceId, branchId);
	}

	public String readTextFile(String filePath) {
		Path path = Paths.get(uploadGitFileDir, filePath);
		try {
			return Files.readString(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			return "파일을 불러오는 중 오류가 발생했습니다.";
		}
	}

	public GitFile getByFilePath(String filePath) {
		System.out.println("filePath : " + filePath);
		return gitFileRepository.findByFilePathIfNotNull(filePath)
				.orElseThrow(() -> new RuntimeException("❌ 파일을 찾을 수 없습니다: " + filePath));
	}

}
