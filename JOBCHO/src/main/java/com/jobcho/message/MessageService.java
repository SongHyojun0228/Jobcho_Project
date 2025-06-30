package com.jobcho.message;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobcho.user.UserRepository;
import com.jobcho.user.Users;
import com.jobcho.websocket.ChatMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final UserRepository userRepository;

	// 🌿 채팅방 메세지 생성 메서드
	public Integer create(ChatMessage msg) {
		Optional<Users> _sender = this.userRepository.findById(msg.getSenderId());
		Users sender = _sender.get();

		Messages m = new Messages();
		m.setChatroomId(msg.getChatroomId());
		m.setSender(sender);
		m.setContent(msg.getContent());
		m.setIsEdited(0);
		m.setIsDeleted(0);
		messageRepository.save(m);
		return m.getMessageId();
	}

	// 🌿 채팅방 메세지 불러오기 메서드
	public List<Messages> getMessage(int chatroomId) {
		List<Messages> messages = messageRepository.findByChatroomId(chatroomId);
		return messages;
	}

	// 🌿 채팅방 메세지 삭제 메서드
	public void deleteMessage(int messageId) {
		Optional<Messages> optionalMessage = messageRepository.findById(messageId);
		if (optionalMessage.isPresent()) {
			Messages m = optionalMessage.get();
			m.setIsDeleted(1);
			messageRepository.save(m);
		}
	}

	// 🌿 채팅방 메세지 수정 메서드
	public void updateMessage(int messageId, String newContent) {
		Optional<Messages> optionalMessage = messageRepository.findById(messageId);

		if (optionalMessage.isPresent()) {
			Messages message = optionalMessage.get();
			message.setContent(newContent);
			message.setIsEdited(1);
			messageRepository.save(message);
		}
	}

	// 🌿 답글 객체 불러오기 메서드(구형)
	public Messages getMessageWithMessageId(int messageId) {
		Optional<Messages> message = this.messageRepository.findById(messageId);
		if (message.isPresent()) {
			return message.get();
		} else {
			return message.orElse(new Messages());
		}
	}

	// 🌿 답글 작성 메서드
	public void addReply(int chatroomId, int parentId, String content, int senderId) {
		Optional<Users> _sender = this.userRepository.findById(senderId);
		Users sender = _sender.get();

		Messages parent = messageRepository.findById(parentId).orElseThrow();
		Messages reply = new Messages();
		reply.setChatroomId(chatroomId);
		reply.setSender(sender);
		reply.setContent(content);
		reply.setIsDeleted(0);
		reply.setIsEdited(0);
		reply.setParentMessage(parent);
		messageRepository.save(reply);
	}

	// 🌿 답글 객체 불러오기 메서드(신규, 이거만 쓸듯)
	public List<Messages> getReplies(int parentMessageId) {
		return messageRepository.findByParentMessage_MessageIdOrderByCreatedDateAsc(parentMessageId);
	}

	public List<Messages> getTopLevelMessagesWithReplies(Integer chatroomId) {
		List<Messages> allMessages = messageRepository.findAllMessagesWithRepliesByChatroomId(chatroomId);

		allMessages.forEach(m -> m.generateHighlightedContent());
		System.out.println("<<< 하이라이트 처리 : MessageService.getTopLevelMessagesWithReplies()");
		for (int i = 0; i < allMessages.size(); i++) {
			System.out.println("allMessage[" + i + "] : " + allMessages.get(i).getContent());
		}

		return allMessages.stream().filter(m -> m.getParentMessage() == null).collect(Collectors.toList());
	}

}
