package com.jobcho.alarm;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobcho.mention.MentionRepository;
import com.jobcho.mention.Mentions;
import com.jobcho.notification.NotificationRepository;
import com.jobcho.notification.Notifications;
import com.jobcho.task.TaskRepository;
import com.jobcho.task.Tasks;
import com.jobcho.user.UserRepository;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;
	private final NotificationRepository notificationRepository;
	private final TaskRepository taskRepository;
	private final MentionRepository mentionsRepository;
	
	// 🌿 알람 생성 메서드
	public void createAlarm(Users user, String type, int targetId, int workspaceId) {
		Alarms alarm = new Alarms();
		alarm.setUser(user);

		switch (type.toUpperCase()) {
		case "NOTIFICATION":
			Optional<Notifications> _notification = notificationRepository.findById(targetId);
			Notifications notifcation = _notification.get();
			alarm.setNotification(notifcation);
			alarm.setWorkspaceId(workspaceId);
			break;

		case "TASK":
			Optional<Tasks> _task = taskRepository.findById(targetId);
			Tasks task = _task.get();
			alarm.setTask(task);
			alarm.setWorkspaceId(workspaceId);
			break;

		case "MENTION":
			Optional<Mentions> _mention = mentionsRepository.findById(targetId);
			Mentions mention = _mention.get();
			alarm.setMention(mention);
			alarm.setWorkspaceId(workspaceId);
			break;

		default:
			throw new IllegalArgumentException(" >>> Unknown alarm type: " + type);
		}

		alarmRepository.save(alarm);
	}

	// 🌿 유저아이디로 알람 불러오기 메서드
	public List<AlarmDTO> getAlarmsByUserId(int userId) {
		List<Alarms> alarms = alarmRepository.findByUserIdAndIsNotRead(userId);

		return alarms.stream().map(alarm -> {
			AlarmDTO dto = new AlarmDTO();
			dto.setUsername(alarm.getUser().getUserName());

			if (alarm.getNotification() != null) {
				dto.setNotificationTitle(alarm.getNotification().getTitle());
			}
			if (alarm.getTask() != null) {
				dto.setTask_title(alarm.getTask().getTaskTitle());
			}
			if (alarm.getMention() != null) {
				dto.setMentionSenderName(alarm.getMention().getSender().getUserName());
			}
			return dto;
		}).collect(Collectors.toList());
	}

	// 🌿 각 워크스페이스에 해당하는 알람 개수를 저장하는 map을 리턴하는 메서드
	public Map<Integer, Integer> getWorkspaceAlarmCountMap(Integer userId) {
		List<WorkspaceAlarmCount> results = alarmRepository.countAlarmsGroupedByWorkspace(userId);
		return results.stream()
				.collect(Collectors.toMap(WorkspaceAlarmCount::getWorkspaceId, WorkspaceAlarmCount::getCnt));
	}

	// 🌿 워크스페이스아이디로 알람 불러오기 메서드
	public List<Alarms> getAlarmsByWorkspaceId(Integer userId, Integer workspaceId) {
		List<Alarms> alarms = alarmRepository.findByWorkspaceId(userId, workspaceId);
		return alarms;
	}

	// 🌿 워크스페이스아이디로 알람 읽음처리 메서드
	public void markAsRead(Integer userId) {
		try {
			this.alarmRepository.markAsRead(userId);		
			System.out.println("🌿 알람 읽음 처리 성공");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("❌ 알람 읽음 처리 실패");
		}
	}
	
}
