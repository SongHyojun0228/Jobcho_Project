package com.jobcho.cs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CsChatroomRepository extends JpaRepository<CsChatroom, Integer> {

	@Query("""
			    SELECT c FROM CS c
			    WHERE c.sentDate = (
			        SELECT MAX(c2.sentDate) FROM CS c2 WHERE c2.csChatroom.csChatroomId = c.csChatroom.csChatroomId
			    )
			    ORDER BY c.sentDate DESC
			""")
	List<CS> findByCsChatroomSentDateOrdered();

	@Query("""
			    SELECT c.csChatroom.csChatroomId, COUNT(c)
			    FROM CS c
			    WHERE c.isRead = 0 AND c.sender.userId <> :userId
			    GROUP BY c.csChatroom.csChatroomId
			""")
	List<Object[]> countUnreadByChatroom(@Param("userId") Integer userId);

}
