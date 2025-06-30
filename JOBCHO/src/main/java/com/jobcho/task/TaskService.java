package com.jobcho.task;

import java.util.List;

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
public class TaskService {

	private final UserRepository userRepository;
	private final ChatroomRepository chatroomRepository;
	private final TaskRepository taskRepository;
	private final ChatroomMemberRepository chatroomMemberRepository;
	private final AlarmRepository alarmRepository;

	// 🌿 할 일 생성 메서드
	public void create(TaskDto dto) {
		Users author = userRepository.findById(dto.getAuthorId()).orElseThrow();
		Chatrooms chatroom = chatroomRepository.findById(dto.getChatroomId()).orElseThrow();

		Tasks task = new Tasks();
		task.setAuthor(author);
		task.setChatroom(chatroom);
		task.setTaskTitle(dto.getTaskTitle());
		task.setDescription(dto.getDescription());
		task.setStatus(0);
		task.setStartDate(dto.getStartDate());
		task.setEndDate(dto.getEndDate());

		taskRepository.save(task);

		// 알람 생성
		List<ChatroomMember> members = chatroomMemberRepository.findByChatroom_ChatroomIdAndUserIsNotNull(dto.getChatroomId());
		for (ChatroomMember member : members) {
			Users user = member.getUser();
			if (!user.getUserId().equals(dto.getAuthorId())) {
				Alarms alarm = new Alarms();
				alarm.setUser(user);
				alarm.setTask(task);
				alarm.setWorkspaceId(dto.getWorkspaceId());
				alarmRepository.save(alarm);
			}
		}
	}

	// 🌿 할 일 수정 메서드
	public void modifyStatus(Tasks tasks, int status) {
		tasks.setStatus(status);
		this.taskRepository.save(tasks);
	}

}
