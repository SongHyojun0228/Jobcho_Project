package com.jobcho.chatroom_member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobcho.chatroom.Chatrooms;
import com.jobcho.user.Users;

import jakarta.transaction.Transactional;

public interface ChatroomMemberRepository extends JpaRepository<ChatroomMember, Integer> {
	boolean existsByChatroom_chatroomIdAndUser_userId(Integer chatroomId, Integer userId);

	List<ChatroomMember> findByChatroom_ChatroomIdAndUserIsNotNull(Integer chatroomId);

	@Query("SELECT m.user FROM ChatroomMember m WHERE m.chatroom.chatroomId = :chatroomId ORDER BY m.user.userName ASC")
	List<Users> findUsersByWorkspaceId(@Param("chatroomId") Integer chatroomId);

	@Query("SELECT COUNT(cm) > 0 FROM ChatroomMember cm WHERE cm.chatroom = :chatroom AND cm.user = :user")
	boolean existsByChatroomAndUser(@Param("chatroom") Chatrooms chatroom, @Param("user") Users user);

	@Query("SELECT cm.chatroom FROM ChatroomMember cm WHERE cm.user.userId = :userId")
	List<Chatrooms> findChatroomsByUserId(@Param("userId") Integer userId);

	@Query("SELECT n FROM ChatroomMember n WHERE n.user.userId = :userId")
	Optional<ChatroomMember> findChatroomMember(@Param("userId") int userId);

	@Modifying
	@Transactional
	@Query("UPDATE ChatroomMember cm SET cm.user = NULL WHERE cm.chatroom.chatroomId = :chatroomId AND cm.user.userId = :userId")
	void nullifyUserFromChatroom(@Param("chatroomId") Integer chatroomId, @Param("userId") Integer userId);

	@Modifying
	@Transactional
	@Query("UPDATE ChatroomMember cm SET cm.user = NULL WHERE cm.chatroom.chatroomId = :chatroomId")
	void nullifyAllUsersFromChatroom(@Param("chatroomId") Integer chatroomId);
}
