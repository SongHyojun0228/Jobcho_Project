package com.jobcho.workspace;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.folder.FolderRepository;
import com.jobcho.folder.Folders;
import com.jobcho.member.MemberService;
import com.jobcho.mychatroom.MyChatroomService;
import com.jobcho.notification.NotificationRepository;
import com.jobcho.notification.Notifications;
import com.jobcho.task.TaskRepository;
import com.jobcho.task.Tasks;
import com.jobcho.user.Users;
import com.jobcho.workspace_domain.WorkspaceDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WorkspaceService {

	private final FolderRepository folderRepository;
	private final ChatroomRepository chatroomRepository;
	private final TaskRepository taskRepository;
	private final NotificationRepository notificationRepository;

	private final WorkspaceRepository workspaceRepository;
	private final MemberService memberService;
	private final WorkspaceDomainService workspaceDomainService;
	private final MyChatroomService myChatroomService;

	public Workspaces getWorkspaceByWorkspaceId(Integer workspaceId) {
		return this.workspaceRepository.findById(workspaceId).get();
	}

	public List<Folders> getFolderWithChatrooms(int workspaceId) {
		List<Folders> folders = folderRepository.findByWorkspaceId(workspaceId);

		for (Folders folder : folders) {
			System.out.println(folder.getFolderId());
			List<Chatrooms> chatrooms = chatroomRepository.findByFolderId(folder.getFolderId());
			folder.setChatrooms(chatrooms);
		}

		return folders;
	}

	public List<Tasks> getTask(int chatroomId) {
		List<Tasks> tasks = taskRepository.findByChatroom_ChatroomId(chatroomId);
		return tasks;
	}

	public List<Tasks> getTasksByUserId(int userId) {
		return taskRepository.findByAuthor_UserId(userId);
	}

	public Tasks getTaskWithTaskId(int taskId) {
		Optional<Tasks> task = this.taskRepository.findById(taskId);
		if (task.isPresent()) {
			return task.get();
		} else {
			return task.orElse(new Tasks());
		}
	}

	public List<Notifications> getNotifi(int chatroomId) {
		List<Notifications> notifications = notificationRepository.findByChatroom_ChatroomId(chatroomId);
		return notifications;
	}

	public Chatrooms getChatroomWithChatId(int chatroomId) {
		Optional<Chatrooms> chatroom = this.chatroomRepository.findById(chatroomId);
		if (chatroom.isPresent()) {
			return chatroom.get();
		} else {
			return chatroom.orElse(new Chatrooms());
		}
	}

	public Integer createWorkspace(Users user, String workspaceName, String workspaceDomain) {
		Workspaces workspace = new Workspaces();
		workspace.setWorkspaceName(workspaceName);
		workspace.setWorkspaceDomain(workspaceDomain);
		workspace.setOwner(user);

		Workspaces savedWorkspace = workspaceRepository.save(workspace);

		memberService.createMember(user, savedWorkspace);
		this.workspaceDomainService.createWorkspaceDomain(workspace.getWorkspaceId(), workspace.getWorkspaceDomain(),
				"/workspace/" + workspace.getWorkspaceId());

		this.myChatroomService.createMyChatroom(user, workspace);

		System.out.println("<<< workspaceRepository.createWorkspace 호출 >>> : 워크스페이스 생성");

		return workspace.getWorkspaceId();
	}

	public String getWorkspaceNameById(Integer workspaceId) {
		Workspaces workspcace = this.workspaceRepository.getById(workspaceId);
		return workspcace.getWorkspaceName();
	}

	public Workspaces getByFolderId(Integer folderId) {
		Folders _folder = this.folderRepository.getById(folderId);
		return workspaceRepository.getById(_folder.getWorkspaceId());
	}

}
