package com.jobcho.chatroom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatrooms, Integer>{
	List<Chatrooms> findByFolderId(int folderId);
}
