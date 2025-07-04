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

	@Query("SELECT gf FROM GitFile gf WHERE gf.filePath = :filePath AND gf.commit.user IS NOT NULL")
	Optional<GitFile> findByFilePathIfNotNull(@Param("filePath") String filePath);

	@Query("SELECT gf FROM GitFile gf " + "WHERE gf.commit.branch.workspace.workspaceId = :workspaceId "
			+ "AND gf.commit.branch.branchId = :branchId " + "ORDER BY gf.commit.uploadedDate DESC")
	List<GitFile> findByWorkspaceIdAndBranchIdOrderByCommitUploadedDateDesc(@Param("workspaceId") Integer workspaceId,
			@Param("branchId") Integer branchId);

}

//Hibernate:SELECT*FROM(SELECT c.*
//FROM commit
//c
//    JOIN
//branch b
//ON b.branch_id=
//c.branch_id
//    JOIN
//workspaces w
//ON w.workspace_id=
//b.workspace_id
//    WHERE w.workspace_id=?
//ORDER BY
//c.uploaded_date DESC)
//WHERE ROWNUM = 1
//
//Hibernate:
//
//select count(gf1_0.file_id) from git_file gf1_0 join commit c1_0 on c1_0.commit_id=gf1_0.commit_id join branch b1_0 on b1_0.branch_id=c1_0.branch_id where gf1_0.commit_id=? and b1_0.workspace_id=?