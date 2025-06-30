package com.jobcho.git;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GitFolderRepository extends JpaRepository<GitFolder, Integer> {

	Optional<GitFolder> findByFolderName(String folderName);

//	@Query("""
//			   	SELECT gf FROM GitFolder gf
//			    JOIN gf.gitFiles gfFile
//			    JOIN gfFile.commit c
//			    JOIN c.branch b
//			    WHERE b.workspace.id = :workspaceId
//			""")
//	List<GitFolder> findAllByWorkspaceId(@Param("workspaceId") Integer workspaceId);

}
