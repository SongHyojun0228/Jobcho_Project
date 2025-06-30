package com.jobcho.git;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobcho.user.Users;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

	List<Branch> findByUser(Users user);

	List<Branch> findByWorkspace_WorkspaceId(int workspaceId);

	@Query("SELECT DISTINCT b FROM Branch b LEFT JOIN FETCH b.commit WHERE b.workspace.workspaceId = :workspaceId")
	List<Branch> findWithCommitsByWorkspaceId(@Param("workspaceId") int workspaceId);

}
