package com.jobcho.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notifications, Integer>{
	List<Notifications> findByChatroom_ChatroomId(int chatroomId);
	
	@Query("SELECT n FROM Notifications n WHERE n.chatroom.chatroomId = :chatroomId AND n.isDeleted = 0")
	Optional<Notifications> findByChatroomId2(@Param("chatroomId") int chatroomId);
}
