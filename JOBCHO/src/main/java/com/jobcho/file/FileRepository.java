package com.jobcho.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobcho.chatroom.Chatrooms;

public interface FileRepository extends JpaRepository<Files, Long> {
	List<Files> findByChatroomAndMessageIsNotNullOrderByUploadedDateAsc(Chatrooms chatroom);
}
