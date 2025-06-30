package com.jobcho.bookmark;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobcho.user.Users;

public interface BookmarkRepository extends JpaRepository<Bookmarks, Integer> {

	// 사용자 ID 기준 북마크 조회
	List<Bookmarks> findByUserUserId(Integer userId);

	// 특정 유저가 특정 Chatroom 을 북마크한 경우 조회
	Optional<Bookmarks> findByUserAndChatroom_ChatroomId(Users user, Integer chatroomId);

	// 특정 유저가 특정 Message 를 북마크한 경우 조회
	Optional<Bookmarks> findByUserAndMessage_MessageId(Users user, Integer messageId);

	// 특정 유저가 특정 Notification 을 북마크한 경우 조회
	Optional<Bookmarks> findByUserAndNotification_NotificationId(Users user, Integer notificationId);

	// 특정 유저가 특정 Task 를 북마크한 경우 조회
	Optional<Bookmarks> findByUserAndTask_TaskId(Users user, Integer taskId);

	// 특정 유저가 특정 Vote 를 북마크한 경우 조회
//	Optional<Bookmarks> findByUserAndVote_VoteId(Users user, Integer voteId);

	// 특정 유저가 특정 File 을 북마크한 경우 조회
//	Optional<Bookmarks> findByUserAndFile_FileId(Users user, Integer fileId);

	// 특정 유저가 특정 MyChatroom 을 북마크한 경우 조회
	Optional<Bookmarks> findByUserAndMyChatroom_MyChatroomId(Users user, Integer myChatroomId);

}
