package com.jobcho.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.alarm.AlarmRepository;
import com.jobcho.alarm.Alarms;
import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.chatroom_member.ChatroomMember;
import com.jobcho.chatroom_member.ChatroomMemberRepository;
import com.jobcho.user.UserRepository;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationService {
	private final AlarmRepository alarmRepository;
	private final ChatroomMemberRepository chatroomMemberRepository;
	private final UserRepository userRepository;
	private final ChatroomRepository chatroomRepository;
	private final NotificationRepository notificationRepository;

	// 🌿 공지사항 생성 및 수정 메서드
	// 존재 시 수정
	// 미 존재 시 생성
	public void createOrUpdate(NotificationDTO dto) {
		Optional<Notifications> existing = notificationRepository.findByChatroomId2(dto.getChatroomId());

		if (existing.isPresent()) {
			Notifications n = existing.get();
			n.setContent(dto.getContent());
			n.setIsEdited(1);
			notificationRepository.save(n);

			// 알람 생성
			List<ChatroomMember> members = chatroomMemberRepository
					.findByChatroom_ChatroomIdAndUserIsNotNull(dto.getChatroomId());
			for (ChatroomMember member : members) {
				Users user = member.getUser();
				if (!user.getUserId().equals(dto.getAuthorId())) {
					Alarms alarm = new Alarms();
					alarm.setUser(user);
					alarm.setNotification(n);
					alarm.setWorkspaceId(dto.getWorkspaceId());
					alarmRepository.save(alarm);
				}
			}
		} else {
			Notifications n = new Notifications();

			Users author = userRepository.findById(dto.getAuthorId()).orElseThrow(() -> new RuntimeException("작성자 없음"));
			Chatrooms chatroom = chatroomRepository.findById(dto.getChatroomId())
					.orElseThrow(() -> new RuntimeException("채팅방 없음"));

			n.setAuthor(author);
			n.setChatroom(chatroom);
			n.setContent(dto.getContent());
			n.setTitle("null");
			n.setIsEdited(0);
			n.setIsDeleted(0);
			notificationRepository.save(n);

			// 알람 생성
			List<ChatroomMember> members = chatroomMemberRepository
					.findByChatroom_ChatroomIdAndUserIsNotNull(dto.getChatroomId());
			for (ChatroomMember member : members) {
				Users user = member.getUser();
				if (!user.getUserId().equals(dto.getAuthorId())) {
					Alarms alarm = new Alarms();
					alarm.setUser(user);
					alarm.setNotification(n);
					alarm.setWorkspaceId(dto.getWorkspaceId());
					alarmRepository.save(alarm);
				}
			}
		}
	}

	public void deleteByChatroomId(int chatroomId) {
		Optional<Notifications> optional = notificationRepository.findByChatroomId2(chatroomId);
		optional.ifPresent(n -> {
			n.setIsDeleted(1);
			notificationRepository.save(n);
		});
	}

}
