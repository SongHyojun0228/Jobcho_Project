package com.jobcho.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobcho.user.Users;
import com.jobcho.workspace.Workspaces;

public interface MemberRepository extends JpaRepository<Members, Integer> {

	@Query("SELECT m.workspace FROM Members m WHERE m.user.userId = :userId ORDER BY m.workspace.workspaceId")
	List<Workspaces> findWorkspacesByUserUserId(@Param("userId") Integer userId);

	@Query("SELECT m.user FROM Members m WHERE m.workspace.workspaceId = :workspaceId ORDER BY m.user.userName ASC")
	List<Users> findUsersByWorkspaceId(@Param("workspaceId") Integer workspaceId);

	boolean existsByWorkspaceAndUser(Workspaces workspace, Users user);

}
