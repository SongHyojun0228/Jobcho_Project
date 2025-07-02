package com.jobcho.git;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

	List<Branch> findByUserUserIdAndWorkspaceWorkspaceId(Integer userId, Integer workspaceId);

	List<Branch> findByWorkspace_WorkspaceId(int workspaceId);

	@Query("SELECT DISTINCT b FROM Branch b LEFT JOIN FETCH b.commit WHERE b.workspace.workspaceId = :workspaceId")
	List<Branch> findWithCommitsByWorkspaceId(@Param("workspaceId") int workspaceId);

	@Query("SELECT b FROM Branch b WHERE b.workspace.workspaceId = :workspaceId AND b.title = :title")
	Optional<Branch> findBranchByWorkspaceIdAndTitle(@Param("workspaceId") Integer workspaceId, @Param("title") String title);

}
