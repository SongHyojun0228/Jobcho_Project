package com.jobcho.folder;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FolderService {

	private final FolderRepository folderRepository;
	private final ChatroomRepository chatroomRepository;

    // 🌿 폴더 생성 메서드
	public void create(int workspaceId, String folderName, int createdBy) {
		Folders f = new Folders();
		f.setWorkspaceId(workspaceId);
		f.setFolderName(folderName);
		f.setCreatedBy(createdBy);
		this.folderRepository.save(f);
	}
	
	public Folders getByChatroomId(Integer chatroomId) {
		Chatrooms _chatroom = chatroomRepository.getById(chatroomId);
		return this.folderRepository.getById(_chatroom.getFolderId());
	}

}
