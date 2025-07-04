package com.jobcho.git;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jobcho.user.UserService;
import com.jobcho.user.Users;
import com.jobcho.workspace.WorkspaceService;
import com.jobcho.workspace.Workspaces;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GitController {

	private final UserService userService;
	private final WorkspaceService workspaceService;
	private final GitFileService gitFileService;
	private final CommitService commitService;
	private final BranchService branchService;
	private final GitFolderService gitFolderService;

	@Value("${file.git-upload-dir}")
	private String uploadGitFileDir;

	// 🌿 깃허브 페이지
//	@GetMapping({"/workspace/{workspaceId}/github", "/workspace/{workspaceId}/github/**"})
//	public String getGitCommitPage(@PathVariable("workspaceId") Integer workspaceId, Principal principal, Model model) {
//
//		Optional<Users> _user = this.userService.getUser(principal.getName());
//		Workspaces workspace = this.workspaceService.getWorkspaceByWorkspaceId(workspaceId);
//		List<GitFolder> allGitFolders = this.gitFolderService.getByWorkspaceId(workspaceId); // 또는 workspaceId 기준
//
//		model.addAttribute("allGitFolders", allGitFolders);
//		List<GitFile> allGitFiles = this.gitFileService.getAllGitFiles();
//		Commit lastCommit = this.commitService.getLastCommit(workspace);
//		List<Branch> myBranches = this.branchService.getByUserId(_user.get());
//
//		model.addAttribute("user", _user.get());
//		model.addAttribute("workspace", workspace);
//		model.addAttribute("allGitFolders", allGitFolders);
//		model.addAttribute("allGitFiles", allGitFiles);
//		model.addAttribute("lastCommit", lastCommit);
//		model.addAttribute("myBranches", myBranches);
//
//		return "git/git_commit";
//	}

	private String getExtension(String fileName) {
		int lastDot = fileName.lastIndexOf(".");
		if (lastDot == -1)
			return "";
		return fileName.substring(lastDot + 1).toLowerCase();
	}

	// 🌿 깃허브 파일 페이지 📄📄
	@GetMapping("/workspace/{workspaceId}/github/view")
	public String browseGitFile(@PathVariable("workspaceId") Integer workspaceId,
			@RequestParam("filePath") String filePath, HttpServletRequest request, Model model, Principal principal) {
		String fullPath = request.getRequestURI();
		String prefix = "/workspace/" + workspaceId + "/github";
		String currentPath = fullPath.length() > prefix.length() ? fullPath.substring(prefix.length() + 1) : "";
		currentPath = URLDecoder.decode(currentPath, StandardCharsets.UTF_8);

		Optional<Users> user = userService.getUser(principal.getName());
		Workspaces workspace = workspaceService.getWorkspaceByWorkspaceId(workspaceId);
		List<Branch> myBranches = this.branchService.getByUserIdAndWorkspaceId(user.get().getUserId(), workspaceId);

		model.addAttribute("currentPath", currentPath);
		model.addAttribute("workspace", workspace);
		model.addAttribute("user", user.get());
		model.addAttribute("myBranches", myBranches);

		// 📄📄 ----------- 파일 보기 JPG, PDF 등등 ----------- 📄📄

		String decodedPath = URLDecoder.decode(filePath, StandardCharsets.UTF_8);

		GitFile file = gitFileService.getByFilePath(decodedPath);
		String extension = getExtension(file.getFileName());

		model.addAttribute("file", file);
		model.addAttribute("fileType", extension);

		return "git/git_view";
	}

	// 🌿 깃허브 페이지 📁📄
	@GetMapping({ "/workspace/{workspaceId}/github", "/workspace/{workspaceId}/github/**" })
	public String browseGitFolder(@PathVariable("workspaceId") Integer workspaceId,
			@RequestParam(value = "branchId", required = false) Integer branchId, HttpServletRequest request,
			Model model, Principal principal) {
		String fullPath = request.getRequestURI();
		String prefix = "/workspace/" + workspaceId + "/github";
		String currentPath = fullPath.length() > prefix.length() ? fullPath.substring(prefix.length() + 1) : "";
		currentPath = URLDecoder.decode(currentPath, StandardCharsets.UTF_8);

		Optional<Users> user = userService.getUser(principal.getName());
		Workspaces workspace = workspaceService.getWorkspaceByWorkspaceId(workspaceId);
		List<Branch> myBranches = this.branchService.getByUserIdAndWorkspaceId(user.get().getUserId(), workspaceId);
		List<Branch> branches = this.branchService.getBranchwithWorkspaceId(workspaceId);

		if (branchId == null) {
			List<Branch> allBranches = branchService.getBranchwithWorkspaceId(workspaceId);
			for (Branch b : allBranches) {
				if ("main".equalsIgnoreCase(b.getTitle())) {
					branchId = b.getBranchId();
					break;
				}
			}
		}

		// 📄📄 ----- 현재 경로에 있는 파일들만 필터링 ----- 📄📄
		List<GitFile> allFiles = gitFileService.getAllGitFilesByWorkspaceIdAndBranchId(workspaceId, branchId);

		String folderPrefix = currentPath.isBlank() ? "" : currentPath + "/";

		List<GitFile> filesInFolder = allFiles.stream().filter(f -> {
			if (f.getFilePath() == null) {
				return false;
			}
			// 경로 앞에 '/' 지우고
			String normalizedPath = f.getFilePath().startsWith("/") ? f.getFilePath().substring(1) : f.getFilePath();

			// normalizedPath가 현재 경로(현재 폴더) folderPrefix로 시작하지 않으면 제외하고
			if (!normalizedPath.startsWith(folderPrefix)) {
				return false;
			}

			// 현재 경로 다음의 나머지 경로를 subPath로 저장 하고
			String subPath = normalizedPath.substring(folderPrefix.length());

			// subPath에 '/'가 없으면?? 현재 폴더에 바로 있는 파일이므로 포함 -> 다음 폴더가 없단 뜻 (하위 폴더 X)
			return !subPath.contains("/");
		}).collect(Collectors.toList());

//		System.out.println("📂 currentPath = " + currentPath);
//		System.out.println("📂 folderPrefix = " + folderPrefix);
//		System.out.println("📂 filesInFolderSize = " + filesInFolder.size());

//		for (GitFile file : allFiles) {
//			if (file.getFilePath() != null) {
//				String np = file.getFilePath().startsWith("/") ? file.getFilePath().substring(1) : file.getFilePath();
//				System.out.println("🗂️ filePath = " + np);
//			}
//		}

		// 📁📁 ----- 하위 폴더 목록 구하기 ----- 📁📁
		Set<String> subfolders = allFiles.stream().map(f -> {
			if (f.getFilePath() == null) {
				return null;
			}

			// 경로 앞에 '/' 지우고
			String normalizedPath = f.getFilePath().startsWith("/") ? f.getFilePath().substring(1) : f.getFilePath();

			// normalizedPath가 현재 경로(현재 폴더) folderPrefix로 시작하지 않으면 제외하고
			if (!normalizedPath.startsWith(folderPrefix)) {
				return null;
			}

			// 현재 경로 다음의 나머지 경로를 subPath로 저장 하고
			String subPath = normalizedPath.substring(folderPrefix.length());

			// subPath에 '/'가 있으면?? 하위 폴더가 있다는 뜻
			if (subPath.contains("/")) {
				return subPath.substring(0, subPath.indexOf("/"));
			} else {
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.toCollection(TreeSet::new));

		Commit lastCommit = this.commitService.getLastCommit(workspace);
		Integer countOfLastCommitFile = this.gitFileService.getLastCommitFileCount(lastCommit, workspace);

		Branch mainBranch = this.branchService.getMainBranchByWorkspaceId(workspaceId);
		
		model.addAttribute("currentPath", currentPath);
		model.addAttribute("subfolders", subfolders);
		model.addAttribute("files", filesInFolder);
		model.addAttribute("workspace", workspace);
		model.addAttribute("user", user.get());
		model.addAttribute("lastCommit", lastCommit);
		model.addAttribute("myBranches", myBranches);
		model.addAttribute("countOfLastCommitFile", countOfLastCommitFile);
		model.addAttribute("branches", branches);
		model.addAttribute("selectedBranchId", branchId);
		model.addAttribute("mainBranch", mainBranch);

		return "git/git_commit";
	}

	// 🌿 커밋
	@PostMapping("/workspace/{workspaceId}/upload/git")
	public String uploadGit(@PathVariable("workspaceId") Integer workspaceId,
			@RequestParam("git_files") MultipartFile[] gitFiles, @RequestParam("commit_content") String commitContent,
			@RequestParam("currentPath") String currentPath, @RequestParam("commit_branch") Integer branchId,
			Model model, Principal principal) {

		Optional<Users> _user = this.userService.getUser(principal.getName());
		
		Branch mainBranch = this.branchService.getMainBranchByWorkspaceId(workspaceId);
		String basePath = currentPath == null || currentPath.isEmpty() ? "" : currentPath + "/";

		Branch branch = this.branchService.getBranchByBranchId(branchId);

		File uploadGitFile = new File(uploadGitFileDir).getAbsoluteFile();
		GitFolder folder = null;
		GitFolder mainFolder = null;

		if (!uploadGitFile.exists()) {
			uploadGitFile.mkdir();
		}

		Commit commit = this.commitService.uploadCommit(branch, commitContent, _user.get());
		Commit mainCommit = this.commitService.uploadCommit(mainBranch, commitContent, null);

		Map<String, GitFolder> folderMap = new HashMap<>();
		Map<String, GitFolder> mainFolderMap = new HashMap<>();

		for (MultipartFile file : gitFiles) {
			String originalName = file.getOriginalFilename();
			if (originalName == null || originalName.trim().isEmpty() || file.getSize() == 0) {
				continue;
			}
			if (originalName.equals(".DS_Store")) {
				continue;
			}

			String relativePath = basePath + originalName;
			String fileName = new File(relativePath).getName();
			String folderPath = relativePath.contains("/") ? relativePath.substring(0, relativePath.lastIndexOf("/"))
					: "(root)";
			System.out.println("폴더 경로 : " + folderPath);
			System.out.println("업로드 파일명: " + relativePath);

			GitFolder gitFolder = folderMap.get(folderPath);
			if (gitFolder == null) {
				if (this.gitFolderService.isExistingFolder(folderPath)) {
					gitFolder = this.gitFolderService.findByPathAndCommit(folderPath, commit);
				} else {
					gitFolder = this.gitFolderService.uploadFolder(folderPath, commit);
				}
				folderMap.put(folderPath, gitFolder);
			}

			GitFolder mainGitFolder = mainFolderMap.get(folderPath);
			if (mainGitFolder == null) {
				if (this.gitFolderService.isExistingFolder(folderPath)) {
					mainGitFolder = this.gitFolderService.findByPathAndCommit(folderPath, mainCommit);
				} else {
					mainGitFolder = this.gitFolderService.uploadFolder(folderPath, mainCommit);
				}
				mainFolderMap.put(folderPath, mainGitFolder);
			}

			File dest = new File(uploadGitFile, relativePath);
			dest.getParentFile().mkdirs();
			try {
				file.transferTo(dest);
			} catch (IOException e) {
				e.printStackTrace();
			}

			this.gitFileService.uploadGitFiles(gitFolder, commit, fileName, relativePath);
			this.gitFileService.uploadGitFiles(mainGitFolder, mainCommit, fileName, relativePath);
		}

		return "redirect:/workspace/" + workspaceId + "/github";
	}

	// 🌿 브랜치 생성
	@PostMapping("/workspace/{workspaceId}/branch/create")
	public String createBranch(@PathVariable("workspaceId") Integer workspaceId,
			@RequestParam("branch_name") String branchName, @RequestParam("branch_r") Integer branchR,
			@RequestParam("branch_g") Integer branchG, @RequestParam("branch_b") String branchB, Principal principal,
			Model model) {

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Workspaces workspace = this.workspaceService.getWorkspaceByWorkspaceId(workspaceId);

		String branchRGB = "(" + branchR + ", " + branchG + ", " + branchB + ")";

		try {
			this.branchService.createBranch(branchName, _user.get(), workspace, branchRGB, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/workspace/" + workspaceId + "/github";
	}

}
