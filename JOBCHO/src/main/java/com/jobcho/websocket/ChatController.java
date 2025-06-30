package com.jobcho.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jobcho.chatroom_member.ChatroomMember;
import com.jobcho.chatroom_member.ChatroomMemberDTO;
import com.jobcho.chatroom_member.ChatroomMemberService;
import com.jobcho.folder.FolderService;
import com.jobcho.folder.Folders;
import com.jobcho.mention.MentionDto;
import com.jobcho.mention.MentionService;
import com.jobcho.message.MessageService;
import com.jobcho.message.MymessageService;
import com.jobcho.user.UserService;
import com.jobcho.workspace.WorkspaceService;
import com.jobcho.workspace.Workspaces;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@ResponseBody
public class ChatController {

	private final MessageService messageService;
	private final ChatroomMemberService chatroomMemberService;
	private final UserService userService;
	private final MentionService mentionService;
	private final MymessageService mymessageService;
	private final FolderService folderService;
	private final WorkspaceService workspaceService;

	@GetMapping("/chatroom/{id}/members")
	public List<ChatroomMemberDTO> getChatroomMembers(@PathVariable("id") Integer id) {
		List<ChatroomMember> members = chatroomMemberService.getChatroomMembersByChatroomId(id);

		return members.stream().map(ChatroomMemberDTO::new).collect(Collectors.toList());
	}

	// 🌿 채팅방 메세지 전송
	@MessageMapping("/chat.sendMessage")
	@Transactional
	public void sendMessage(ChatMessage message) {
		System.out.println(">>> incoming message: " + message);
		Integer messageId = this.messageService.create(message);

		List<String> validMentions = new ArrayList<>();

		// 멘션이 포함된 메세지일 때
		if (message.getMentions() != null) {
			for (String mentionUserName : message.getMentions()) {
				String mentionName = mentionUserName.startsWith("@") ? mentionUserName.substring(1) : mentionUserName;
				Integer receiverId = userService.findUserIdByUserName(mentionName);
				System.out.println("🌿 mention user: " + mentionUserName);
//				Integer receiverId = userService.findUserIdByUserName(mentionUserName);

				// 멘션한 사용자가 있을 때
				if (receiverId != null) {
					MentionDto mentionDto = new MentionDto();
					mentionDto.setMessageId(messageId);
					mentionDto.setChatroomId(message.getChatroomId());
					mentionDto.setSenderId(message.getSenderId());
					mentionDto.setReceiverId(receiverId);

					Folders folder = this.folderService.getByChatroomId(message.getChatroomId());
					Workspaces workspace = this.workspaceService.getByFolderId(folder.getFolderId());
					mentionDto.setWorkspaceId(workspace.getWorkspaceId());

					mentionService.saveMention(mentionDto);
					System.out.println("🌿 채팅방메세지 전송 + 멘션 등록 컨트롤러 수행");
//					validMentions.add(mentionUserName);
					validMentions.add("@" + mentionName);
				}
			}
		}

		message.setMentions(validMentions);

		MessageResponse response = new MessageResponse(messageId, message.getSender(), message.getContent(),
				message.getChatroomId(), validMentions, message.getFileName(), message.getProfile());

		messagingTemplate.convertAndSend("/topic/chatroom/" + message.getChatroomId(), response);
	}

	// 🌿 나와의 채팅방 메세지 전송
	@MessageMapping("/chat.sendMymessage")
	public void sendMymessage(ChatMessage message) {
		System.out.println("마이챗 호출됨");
		this.mymessageService.create(message);
		messagingTemplate.convertAndSend("/topic/chatroom/mychat/" + message.getChatroomId(), message);
	}

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
}
