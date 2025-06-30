package com.jobcho.message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MymessageRepository extends JpaRepository<Mymessage, Integer>{
	List<Mymessage> findByMyChatroomId(int mychatroomId);
}
