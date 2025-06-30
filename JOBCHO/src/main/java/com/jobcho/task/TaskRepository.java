package com.jobcho.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tasks, Integer>{
//	List<Tasks> findByChatroomId(int chatroomId);
	List<Tasks> findByChatroom_ChatroomId(int chatroomId);
	
	List<Tasks> findByAuthor_UserId(Integer userId);

}
