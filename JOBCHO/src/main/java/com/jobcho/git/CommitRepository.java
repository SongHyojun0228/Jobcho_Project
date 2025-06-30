package com.jobcho.git;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobcho.workspace.Workspaces;

public interface CommitRepository extends JpaRepository<Commit, Integer>{
	
	Optional<Commit> findTopByBranch_WorkspaceOrderByUploadedDateDesc(Workspaces workspace);
	
}
