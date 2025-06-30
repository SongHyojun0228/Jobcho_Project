package com.jobcho.mychatroom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyChatroomRepository extends JpaRepository<MyChatroom, Integer>{
	Optional<MyChatroom> findByUser_UserId(int userId);
}
