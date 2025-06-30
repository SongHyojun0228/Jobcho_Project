package com.jobcho.git;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobcho.workspace.Workspaces;

public interface GitFileRepository extends JpaRepository<GitFile, Integer> {

	List<GitFile> findAllByOrderByCommitUploadedDateDesc();

	@Query("""
			SELECT COUNT(f)
			FROM GitFile f
			WHERE f.commit = :commit
			  AND f.commit.branch.workspace = :workspace
			""")
	Integer countByCommitAndWorkspace(@Param("commit") Commit commit, @Param("workspace") Workspaces workspace);

	Optional<GitFile> findByFilePath(String filePath);
}
