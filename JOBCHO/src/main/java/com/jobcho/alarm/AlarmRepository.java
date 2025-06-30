package com.jobcho.alarm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface AlarmRepository extends JpaRepository<Alarms, Integer> {

	@Query("""
			SELECT a FROM Alarms a
			WHERE a.user.userId = :userId
			AND a.isRead = 0
			""")
	List<Alarms> findByUserIdAndIsNotRead(@Param("userId") Integer userId);

	// 워크스페이스 내에서 알람 리스트 보기
	@Query("""
			SELECT a.workspaceId AS workspaceId, COUNT(a) AS cnt
			FROM Alarms a
			WHERE a.user.userId = :userId
			AND a.isRead = 0
			GROUP BY a.workspaceId
			""")
	List<WorkspaceAlarmCount> countAlarmsGroupedByWorkspace(@Param("userId") Integer userId);

	// 알람 읽음 처리
	@Query("SELECT a FROM Alarms a WHERE a.user.userId = :userId AND a.workspaceId = :workspaceId ORDER BY a.createdDate DESC")
	List<Alarms> findByWorkspaceId(@Param("userId") Integer userId, @Param("workspaceId") Integer workspaceId);

	@Transactional
	@Modifying
	@Query("""
			UPDATE Alarms a
			SET a.isRead = 1
			WHERE a.user.userId = :userId
			AND a.isRead = 0
			""")
	void markAsRead(@Param("userId") Integer userId);

}
