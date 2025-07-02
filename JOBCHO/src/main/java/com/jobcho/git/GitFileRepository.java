package com.jobcho.git;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobcho.workspace.Workspaces;

public interface GitFileRepository extends JpaRepository<GitFile, Integer> {

	@Query("""
			SELECT gf FROM GitFile gf
			WHERE gf.commit.branch.workspace.workspaceId = :workspaceId
			ORDER BY gf.commit.uploadedDate DESC
			""")
	List<GitFile> findAllByWorkspaceIdOrderByCommitUploadedDateDesc(@Param("workspaceId") Integer workspaceId);

	@Query("""
			SELECT COUNT(f)
			FROM GitFile f
			WHERE f.commit = :commit
			  AND f.commit.branch.workspace = :workspace
			""")
	Integer countByCommitAndWorkspace(@Param("commit") Commit commit, @Param("workspace") Workspaces workspace);

	Optional<GitFile> findByFilePath(String filePath);

	@Query("SELECT gf FROM GitFile gf " + "WHERE gf.commit.branch.workspace.workspaceId = :workspaceId "
			+ "AND gf.commit.branch.branchId = :branchId " + "ORDER BY gf.commit.uploadedDate DESC")
	List<GitFile> findByWorkspaceIdAndBranchIdOrderByCommitUploadedDateDesc(@Param("workspaceId") Integer workspaceId,
			@Param("branchId") Integer branchId);

}
