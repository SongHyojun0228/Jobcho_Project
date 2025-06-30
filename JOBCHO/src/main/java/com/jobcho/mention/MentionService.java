package com.jobcho.mention;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.alarm.AlarmRepository;
import com.jobcho.alarm.Alarms;
import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.message.MessageRepository;
import com.jobcho.message.Messages;
import com.jobcho.user.UserRepository;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MentionService {

	private final UserRepository userRepository;
	private final MentionRepository mentionRepository;
	private final ChatroomRepository chatroomRepository;
	private final AlarmRepository alarmRepository;
	private final MessageRepository messageRepository;
	
	// 🌿 멘션 저장 메서드
	public void saveMention(MentionDto dto) {
		Mentions mention = new Mentions();
		
		Optional<Chatrooms> _chatroom = chatroomRepository.findById(dto.getChatroomId());
		Optional<Users> _sender = userRepository.findById(dto.getSenderId());
		Optional<Users> _receiver = userRepository.findById(dto.getReceiverId());
		Optional<Messages> _message = messageRepository.findById(dto.getMessageId());
		
		mention.setMessage(_message.get());
		mention.setChatroom(_chatroom.get());
		mention.setSender(_sender.get());
		mention.setReceiver(_receiver.get());
		
		mentionRepository.save(mention);

		// 알람 생성
		Alarms alarm = new Alarms();
		alarm.setUser(_receiver.get());
		alarm.setMention(mention);
		alarm.setWorkspaceId(dto.getWorkspaceId());
		alarmRepository.save(alarm);

		System.out.println("🌿 멘션 저장 메서드 수행");
	}
	
	public List<Mentions> getByChatroomId(Integer chatroomId) {
		return this.mentionRepository.findByChatroom_chatroomId(chatroomId);
	}
}
