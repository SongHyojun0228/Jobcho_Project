package com.jobcho.workspace;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jobcho.alarm.AlarmRepository;
import com.jobcho.alarm.AlarmService;
import com.jobcho.alarm.Alarms;
import com.jobcho.bookmark.BookmarkService;
import com.jobcho.bookmark.Bookmarks;
import com.jobcho.chatroom.ChatroomService;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.chatroom_member.ChatroomMemberService;
import com.jobcho.folder.FolderService;
import com.jobcho.folder.Folders;
import com.jobcho.member.MemberService;
import com.jobcho.message.MessageService;
import com.jobcho.message.Messages;
import com.jobcho.mychatroom.MyChatroom;
import com.jobcho.mychatroom.MyChatroomService;
import com.jobcho.notification.NotificationDTO;
import com.jobcho.notification.NotificationService;
import com.jobcho.notification.Notifications;
import com.jobcho.task.TaskDto;
import com.jobcho.task.TaskService;
import com.jobcho.task.Tasks;
import com.jobcho.user.UserRepository;
import com.jobcho.user.UserService;
import com.jobcho.user.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class WorkspaceSideBarController {

	private final FolderService folderService;
	private final NotificationService notificationService;
	private final BookmarkService bookmarkService;
	private final WorkspaceService workspaceService;
	private final TaskService taskService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final MemberService memberService;
	private final ChatroomMemberService chatroomMemberService;
	private final MessageService messageService;
	private final AlarmService alarmService;
	private final AlarmRepository alarmRepository;
	private final MyChatroomService myChatroomService;
	private final ChatroomService chatroomService;

	// 🌿 사이드 바 관련 컨트롤러 메서드
	// 🌿 사이드바 할 일 자세히 보기 GET
	@GetMapping("/workspace/{workspaceId}/{chatroomId}/side/{taskId}")
	public String workspaceMain_SidebarTask(@PathVariable("workspaceId") int workspaceId,
			@PathVariable("chatroomId") int chatroomId, @PathVariable("taskId") int taskId, Model model,
			Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		List<Users> members = this.memberService.findUsersByWorkspaceId(workspaceId);
		List<Users> chatroomMembers = this.chatroomMemberService.findUsersByChatroomId(chatroomId);

		List<Folders> folders = workspaceService.getFolderWithChatrooms(workspaceId);
		List<Tasks> tasks = workspaceService.getTask(chatroomId);
		List<Notifications> notifications = workspaceService.getNotifi(chatroomId);
		List<Messages> messages = messageService.getTopLevelMessagesWithReplies(chatroomId);
		List<Bookmarks> bookmarks = bookmarkService.getBookmarksByUserId(user.getUserId());
		Set<Integer> bookmarkedChatroomIds = bookmarkService.extractChatroomBookmarkIds(bookmarks);
		Set<Integer> bookmarkedMyChatroomIds = bookmarkService.extractMyChatroomBookmarkIds(bookmarks);
		Set<Integer> bookmarkedMessageIds = bookmarkService.extractMessageBookmarkIds(bookmarks);

		Chatrooms chatrooms = this.workspaceService.getChatroomWithChatId(chatroomId);
		Tasks tasksDetail = this.workspaceService.getTaskWithTaskId(taskId);
		MyChatroom mychat = myChatroomService.findMychatByUserID(user.getUserId());
		List<Chatrooms> userChatrooms = chatroomMemberService.getChatroomByUserId(user.getUserId());

		model.addAttribute("user", user);
		model.addAttribute("members", members);
		model.addAttribute("chatroomMembers", chatroomMembers);
		model.addAttribute("workspaceId", workspaceId);
		model.addAttribute("folders", folders);
		model.addAttribute("chatroomId", chatroomId);
		model.addAttribute("userChatrooms", userChatrooms);
		model.addAttribute("tasks", tasks);
		model.addAttribute("chatrooms", chatrooms);
		model.addAttribute("notifications", notifications);
		model.addAttribute("messages", messages);
		model.addAttribute("tasksDetail", tasksDetail);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("bookmarkedChatroomIds", bookmarkedChatroomIds);
		model.addAttribute("bookmarkedMyChatroomIds", bookmarkedMyChatroomIds);
		model.addAttribute("bookmarkedMessageIds", bookmarkedMessageIds);
		model.addAttribute("mychat", mychat);

		return "workspace/workspace_sidebar_taskDetail";
	}

	// 🌿 사이드바 채팅 수정 GET
	@GetMapping("/workspace/{workspaceId}/{chatroomId}/side/modifyChat")
	public String workspaceMain_SidebarChange(@PathVariable("workspaceId") int workspaceId,
			@PathVariable("chatroomId") int chatroomId, Model model, Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		List<Users> members = this.memberService.findUsersByWorkspaceId(workspaceId);
		List<Users> chatroomMembers = this.chatroomMemberService.findUsersByChatroomId(chatroomId);

		List<Folders> folders = workspaceService.getFolderWithChatrooms(workspaceId);
		List<Tasks> tasks = workspaceService.getTask(chatroomId);
		List<Notifications> notifications = workspaceService.getNotifi(chatroomId);
		List<Messages> messages = messageService.getTopLevelMessagesWithReplies(chatroomId);
		List<Bookmarks> bookmarks = bookmarkService.getBookmarksByUserId(user.getUserId());
		Set<Integer> bookmarkedChatroomIds = bookmarkService.extractChatroomBookmarkIds(bookmarks);
		Set<Integer> bookmarkedMyChatroomIds = bookmarkService.extractMyChatroomBookmarkIds(bookmarks);
		Set<Integer> bookmarkedMessageIds = bookmarkService.extractMessageBookmarkIds(bookmarks);

		Chatrooms chatrooms = this.workspaceService.getChatroomWithChatId(chatroomId);
		MyChatroom mychat = myChatroomService.findMychatByUserID(user.getUserId());
		List<Chatrooms> userChatrooms = chatroomMemberService.getChatroomByUserId(user.getUserId());

		model.addAttribute("user", user);
		model.addAttribute("members", members);
		model.addAttribute("chatroomMembers", chatroomMembers);
		model.addAttribute("workspaceId", workspaceId);
		model.addAttribute("folders", folders);
		model.addAttribute("chatroomId", chatroomId);
		model.addAttribute("userChatrooms", userChatrooms);
		model.addAttribute("tasks", tasks);
		model.addAttribute("chatrooms", chatrooms);
		model.addAttribute("notifications", notifications);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("bookmarkedChatroomIds", bookmarkedChatroomIds);
		model.addAttribute("bookmarkedMyChatroomIds", bookmarkedMyChatroomIds);
		model.addAttribute("bookmarkedMessageIds", bookmarkedMessageIds);
		model.addAttribute("messages", messages);
		model.addAttribute("mychat", mychat);

		return "workspace/workspace_sidebar_modifychat";
	}

	// 🌿 사이드바 메시지 답글 GET
	@GetMapping("/workspace/{workspaceId}/{chatroomId}/side/message/{messageId}")
	public String workspaceMain_SidebarMessage(@PathVariable("workspaceId") int workspaceId,
			@PathVariable("chatroomId") int chatroomId, @PathVariable("messageId") int messageId, Model model,
			Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		List<Users> members = this.memberService.findUsersByWorkspaceId(workspaceId);
		List<Users> chatroomMembers = this.chatroomMemberService.findUsersByChatroomId(chatroomId);

		List<Folders> folders = workspaceService.getFolderWithChatrooms(workspaceId);
		List<Tasks> tasks = workspaceService.getTask(chatroomId);
		List<Notifications> notifications = workspaceService.getNotifi(chatroomId);
		List<Messages> messages = messageService.getTopLevelMessagesWithReplies(chatroomId);
		List<Messages> replies = messageService.getReplies(messageId);
		List<Bookmarks> bookmarks = bookmarkService.getBookmarksByUserId(user.getUserId());

		Set<Integer> bookmarkedChatroomIds = bookmarkService.extractChatroomBookmarkIds(bookmarks);
		Set<Integer> bookmarkedMyChatroomIds = bookmarkService.extractMyChatroomBookmarkIds(bookmarks);
		Set<Integer> bookmarkedMessageIds = bookmarkService.extractMessageBookmarkIds(bookmarks);

		Chatrooms chatrooms = this.workspaceService.getChatroomWithChatId(chatroomId);
		Messages searchMessage = messageService.getMessageWithMessageId(messageId);
		MyChatroom mychat = myChatroomService.findMychatByUserID(user.getUserId());
		List<Chatrooms> userChatrooms = chatroomMemberService.getChatroomByUserId(user.getUserId());

		model.addAttribute("user", user);
		model.addAttribute("members", members);
		model.addAttribute("chatroomMembers", chatroomMembers);
		model.addAttribute("workspaceId", workspaceId);
		model.addAttribute("folders", folders);
		model.addAttribute("chatroomId", chatroomId);
		model.addAttribute("userChatrooms", userChatrooms);
		model.addAttribute("tasks", tasks);
		model.addAttribute("chatrooms", chatrooms);
		model.addAttribute("notifications", notifications);
		model.addAttribute("messages", messages);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("bookmarkedChatroomIds", bookmarkedChatroomIds);
		model.addAttribute("bookmarkedMyChatroomIds", bookmarkedMyChatroomIds);
		model.addAttribute("bookmarkedMessageIds", bookmarkedMessageIds);
		model.addAttribute("searchMessage", searchMessage);
		model.addAttribute("replies", replies);
		model.addAttribute("mychat", mychat);

		return "workspace/workspace_sidebar_message";
	}

	// 🌿 채팅방 정보 수정 POST
	@PostMapping("/workspace/{workspaceId}/{chatroomId}/chatroomModify")
	public String workspaceMain_SidebarChat_Modifiy(@PathVariable("workspaceId") int workspaceId,
			@PathVariable("chatroomId") int chatroomId, Model model, Principal principal,
			@RequestParam("chatroom_discription") String chatroomName, @RequestParam("chatName") String discription) {
		chatroomService.updateChatroom(chatroomId, discription, chatroomName);
		return String.format("redirect:/workspace/" + workspaceId + "/" + chatroomId);
	}

	// 🌿 할 일 생성 POST
	@PostMapping("workspace/{workspaceId}/{chatroomId}/taskcreate")
	public String taskcreate(@PathVariable("workspaceId") int workspaceId, @PathVariable("chatroomId") int chatroomId,
			@RequestParam("taskTitle") String content, @RequestParam("description") String description,
			@RequestParam("startDate") String startdate, @RequestParam("endDate") String enddate, Principal principal) {

		Optional<Users> _user = userService.getUser(principal.getName());
		Users user = _user.get();

		LocalDateTime startDate = LocalDateTime.parse(startdate);
		LocalDateTime endDate = LocalDateTime.parse(enddate);

		TaskDto dto = new TaskDto();
		dto.setAuthorId(user.getUserId());
		dto.setChatroomId(chatroomId);
		dto.setTaskTitle(content);
		dto.setDescription(description);
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);
		dto.setWorkspaceId(workspaceId);

		try {
			taskService.create(dto);
			return String.format("redirect:/workspace/%d/%d", workspaceId, chatroomId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// 🌿 할 일 진행률 업데이트 POST
	@PostMapping("/workspace/{workspaceId}/{chatroomId}/side/{taskId}/modify")
	public String workspaceMain_SidebarTask_Modifiy(@PathVariable("workspaceId") int workspaceId,
			@PathVariable("chatroomId") int chatroomId, @PathVariable("taskId") int taskId,
			@RequestParam(value = "status") int status, Model model, Principal principal) {
		System.out.println("진도율 수정 요청");
		Tasks tasksDetail = this.workspaceService.getTaskWithTaskId(taskId);
		this.taskService.modifyStatus(tasksDetail, status);
		return String.format("redirect:/workspace/" + workspaceId + "/" + chatroomId + "/side/" + taskId);
	}

	// 🌿 공지사항 등록 POST
	@PostMapping("workspace/{workspaceId}/{chatroomId}/notificationcreate")
	public String notificationcreate(@PathVariable("workspaceId") int workspaceId,
			@PathVariable("chatroomId") int chatroomId, @RequestParam(value = "notification_content") String content,
			Principal principal) {

		Optional<Users> _user = userService.getUser(principal.getName());
		Users user = _user.get();

		NotificationDTO dto = new NotificationDTO();
		dto.setAuthorId(user.getUserId());
		dto.setChatroomId(chatroomId);
		dto.setWorkspaceId(workspaceId);
		dto.setContent(content);

		try {
			notificationService.createOrUpdate(dto);
			return String.format("redirect:/workspace/%d/%d", workspaceId, chatroomId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// 🌿 공지사항 삭제 POST
	@PostMapping("/workspace/{workspaceId}/{chatroomId}/notification/delete")
	public String deleteNotification(@PathVariable("workspaceId") int workspaceId,
			@PathVariable("chatroomId") int chatroomId) {
		notificationService.deleteByChatroomId(chatroomId);
		return "redirect:/workspace/" + workspaceId + "/" + chatroomId;
	}

	// 🌿 즐겨찾기 등록 POST
	@PostMapping("/workspace/bookmark/toggle")
	@ResponseBody
	public Map<String, Object> bookmarkCreate(@RequestBody Map<String, String> payload, Principal principal) {
		Map<String, Object> result = new HashMap<>();
		try {
			String type = payload.get("type"); // chatroom? messages? etc
			int targetId = Integer.parseInt(payload.get("targetId")); // date-set-?-id
			String action = payload.get("action"); // isAdding ? 'ADD' : 'REMOVE'

			Optional<Users> _user = this.userService.getUser(principal.getName());
			Users user = _user.get();

			if ("ADD".equals(action)) {
				this.bookmarkService.createBookmark(user, type, targetId);
				System.out.println("즐겨찾기 추가 >> user : " + user.getUserName() + ", type : " + type);
			} else {
				this.bookmarkService.deleteBookmark(user, type, targetId);
				System.out.println("즐겨찾기 삭제 >> user : " + user.getUserName() + ", type : " + type);
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}

		return result;
	}

	// 🌿 조직도 GET
	@GetMapping("{workspaceId}/organization")
	public String getOrganizationChar(@PathVariable("workspaceId") int workspaceId, Principal principal, Model model) {
		List<Users> members = this.memberService.findUsersByWorkspaceId(workspaceId);
		model.addAttribute("members", members);

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();

		model.addAttribute("user", user);

		System.out.println("member.size() : " + members.size());
		return "workspace/organization_chart";
	}

	// 🌿 알람 사이드바 GET
	@GetMapping("/{workspaceId}/alarm")
	public String getAlarm(@PathVariable("workspaceId") int workspaceId, Principal principal, Model model) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		List<Alarms> alarmList = alarmService.getAlarmsByWorkspaceId(user.getUserId(), workspaceId);

		Integer maxAlarmId = alarmList.stream().map(Alarms::getAlarmId).max(Integer::compareTo).orElse(0);

		model.addAttribute("user", user);
		model.addAttribute("lastAlarmId", maxAlarmId);
		model.addAttribute("alarmList", alarmList);
		model.addAttribute("workspaceId", workspaceId);

		this.alarmService.markAsRead(user.getUserId());

		return "workspace/side_alarm";
	}

	// 🌿 알람 읽음 처리
	@ResponseBody
	@GetMapping("/{workspaceId}/alarm/mark")
	public void alarmMarkAs(@PathVariable("workspaceId") int workspaceId, Principal principal, Model model) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();

		this.alarmService.markAsRead(user.getUserId());
	}

	@GetMapping("/{workspaceId}/alarm/list")
	public String getAlarmList(@PathVariable("workspaceId") int workspaceId, Model model, Principal principal) {
		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();

		List<Alarms> alarmList = alarmService.getAlarmsByWorkspaceId(user.getUserId(), workspaceId);
		model.addAttribute("alarmList", alarmList);

//		this.alarmService.markAsRead(user.getUserId());

		return "workspace/side_alarm :: alarmListFragment";
	}
}
