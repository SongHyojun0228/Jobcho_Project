package com.jobcho.mention;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mentions, Integer> {
	
	List<Mentions> findByChatroom_chatroomId(Integer chatroomId);

}
