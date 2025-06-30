package com.jobcho.message;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Messages, Integer> {
	List<Messages> findByChatroomId(int chatroomId);

	List<Messages> findByParentMessage_MessageIdOrderByCreatedDateAsc(Integer parentMessageId);

	@Query("SELECT m FROM Messages m LEFT JOIN FETCH m.replies WHERE m.chatroomId = :chatroomId ORDER BY m.createdDate ASC")
	List<Messages> findAllMessagesWithRepliesByChatroomId(@Param("chatroomId") Integer chatroomId);

	@Query("SELECT m FROM Messages m LEFT JOIN FETCH m.files WHERE m.messageId = :messageId")
	Optional<Messages> findByIdWithFiles(@Param("messageId") Integer messageId);
}
