package com.jobcho.git;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobcho.workspace.Workspaces;

public interface CommitRepository extends JpaRepository<Commit, Integer> {

	@Query(value = """
			    SELECT *
			    FROM (
			        SELECT c.*
			        FROM commit c
			        JOIN branch b ON b.branch_id = c.branch_id
			        JOIN workspaces w ON w.workspace_id = b.workspace_id
			        WHERE w.workspace_id = :#{#workspace.workspaceId}
			        ORDER BY c.uploaded_date DESC
			    )
			    WHERE ROWNUM = 1
			""", nativeQuery = true)
	Optional<Commit> findTopByBranch_WorkspaceOrderByUploadedDateDesc(@Param("workspace") Workspaces workspace);
}
