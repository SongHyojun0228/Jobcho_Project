package com.jobcho.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobcho.chatroom.ChatroomRepository;
import com.jobcho.chatroom.Chatrooms;
import com.jobcho.message.MessageRepository;
import com.jobcho.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileService {

	private final FileRepository fileRepository;
	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	private final ChatroomRepository chatRoomRepository;

	@Value("${file.file-upload-dir}")
	private String uploadDir;

	public void uploadFile(MultipartFile file, Integer messageId, Integer senderId, Integer chatroomId)
			throws IOException {
		Files entity = new Files();
		entity.setFileName(file.getOriginalFilename());
		entity.setSender(userRepository.findById(senderId).orElseThrow());
		entity.setChatroom(chatRoomRepository.findById(chatroomId).orElseThrow());
		entity.setMessage(messageRepository.findById(messageId).orElseThrow());

		File directory = new File(uploadDir).getAbsoluteFile();
		System.out.println("file dir : " + uploadDir);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		File dest = new File(directory, file.getOriginalFilename());
		try {
			file.transferTo(dest);
			fileRepository.save(entity);
		} catch (Exception e) {
			System.out.println("니기랄");
			e.printStackTrace();
		}
	}

	public List<Files> getFilesByChatroom(Chatrooms chatroom) {
		return fileRepository.findByChatroomAndMessageIsNotNullOrderByUploadedDateAsc(chatroom);
	}
}