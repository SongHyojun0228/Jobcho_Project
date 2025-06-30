package com.jobcho.bookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.message.MessageRepository;
import com.jobcho.message.Messages;
import com.jobcho.mychatroom.MyChatroom;
import com.jobcho.mychatroom.MyChatroomRepository;
import com.jobcho.notification.NotificationRepository;
import com.jobcho.notification.Notifications;
import com.jobcho.task.TaskRepository;
import com.jobcho.task.Tasks;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final MessageRepository messageRepository;
	private final NotificationRepository notificationRepository;
	private final TaskRepository taskRepository;
	private final ChatroomRepository chatroomRepository;
	private final MyChatroomRepository myChatroomRepository;

	// 🌿 유저아이디로 즐겨찾기 불러오기 메서드
	public List<Bookmarks> getBookmarksByUserId(Integer userId) {
		return this.bookmarkRepository.findByUserUserId(userId);
	}

	// 🌿 즐겨찾기 생성 메서드
	public void createBookmark(Users user, String type, int targetId) {

		Bookmarks b = new Bookmarks();
		b.setUser(user);
		switch (type.toUpperCase()) {
		case "MESSAGE":
			Optional<Messages> _message = this.messageRepository.findById(targetId);
			b.setMessage(_message.get());
			break;
		case "NOTIFICATION":
			Optional<Notifications> _notification = this.notificationRepository.findById(targetId);
			b.setNotification(_notification.get());
			break;
		case "VOTE":
			b.setVoteId(targetId);
			break;
		case "TASK":
			Optional<Tasks> _task = this.taskRepository.findById(targetId);
			b.setTask(_task.get());
			break;
		case "CHATROOM":
			Optional<Chatrooms> _chatroom = this.chatroomRepository.findById(targetId);
			b.setChatroom(_chatroom.get());
			break;
		case "FILE":
			b.setFileId(targetId);
			break;
		case "MYCHATROOM":
			Optional<MyChatroom> _myChatroom = this.myChatroomRepository.findById(targetId);
			b.setMyChatroom(_myChatroom.get());
			break;
		}
		bookmarkRepository.save(b);
	}

	// 🌿 즐겨찾기 삭제 메서드 (리팩토링)
	public void deleteBookmark(Users user, String type, Integer targetId) {
		Optional<Bookmarks> bookmarkOptional = Optional.empty();

		switch (type.toUpperCase()) {
		case "CHATROOM":
			bookmarkOptional = bookmarkRepository.findByUserAndChatroom_ChatroomId(user, targetId);
			break;
		case "MESSAGE":
			bookmarkOptional = bookmarkRepository.findByUserAndMessage_MessageId(user, targetId);
			break;
		case "NOTIFICATION":
			bookmarkOptional = bookmarkRepository.findByUserAndNotification_NotificationId(user, targetId);
			break;
		case "TASK":
			bookmarkOptional = bookmarkRepository.findByUserAndTask_TaskId(user, targetId);
			break;
//		case "VOTE":
//			bookmarkOptional = bookmarkRepository.findByUserAndVote_VoteId(user, targetId);
//			break;
//		case "FILE":
//			bookmarkOptional = bookmarkRepository.findByUserAndFile_FileId(user, targetId);
//			break;
		case "MYCHATROOM":
			bookmarkOptional = bookmarkRepository.findByUserAndMyChatroom_MyChatroomId(user, targetId);
			break;
		default:
			throw new IllegalArgumentException("Unsupported bookmark type: " + type);
		}

		bookmarkOptional.ifPresent(bookmarkRepository::delete);
	}
	
	public Set<Integer> extractChatroomBookmarkIds(List<Bookmarks> bookmarks) {
		return bookmarks.stream().map(Bookmarks::getChatroomId).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	public Set<Integer> extractMyChatroomBookmarkIds(List<Bookmarks> bookmarks) {
		return bookmarks.stream().map(Bookmarks::getMyChatroomId).filter(Objects::nonNull).collect(Collectors.toSet());
	}
	
	public Set<Integer> extractMessageBookmarkIds(List<Bookmarks> bookmarks) {
		return bookmarks.stream().map(Bookmarks::getMessageId).filter(Objects::nonNull).collect(Collectors.toSet());
	}
}
