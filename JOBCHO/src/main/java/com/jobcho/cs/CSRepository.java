package com.jobcho.cs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CSRepository extends JpaRepository<CS, Integer> {

	// 🌿 해당 채팅방의 모든 채팅 불러오기 쿼리 수행
	@Query("SELECT c FROM CS c WHERE c.csChatroom.csChatroomId = :csChatroomId ORDER BY c.csId")
	List<CS> findByCsChatroomIdOrdered(@Param("csChatroomId") Integer csChatroomId);

	// 🌿 읽음 처리 쿼리 수행
	@Modifying
	@Query("""
			    UPDATE CS c
			    SET c.isRead = 1
			    WHERE c.csChatroom.csChatroomId = :csChatroomId AND c.sender.userId <> :userId AND c.isRead = 0

			""")
	void markAsReadByChatroomId(@Param("csChatroomId") Integer csChatroomId, @Param("userId") Integer userId);

	// 🌿 마지막 채팅 가져오기 쿼리 수행
	@Query("""
			    SELECT c FROM CS c
			    WHERE c.sentDate IN (
			        SELECT MAX(c2.sentDate)
			        FROM CS c2
			        GROUP BY c2.csChatroom.csChatroomId
			    )
			""")
	List<CS> findLatestMessagesPerChatroom();

}
