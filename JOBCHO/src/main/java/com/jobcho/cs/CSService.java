package com.jobcho.cs;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobcho.user.UserRepository;
import com.jobcho.user.Users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CSService {

	private final CSRepository csRepository;
	private final UserRepository userRepository;
	private final CsChatroomRepository csChatroomRepository;

    // 🌿 1:1 고객센터 채팅 생성 메서드
	public void create(CsChatMessage msg) {
		try {
			Optional<Users> _sender = this.userRepository.findById(msg.getSenderId());
			Users sender = _sender.get();
			
			Optional<CsChatroom> _csChatroom = this.csChatroomRepository.findById(msg.getCsChatroomId());
			System.out.println("🌿 CSRepository.create : " + msg.getCsChatroomId());
			System.out.println("🌿 _csChatroom : " + _csChatroom);
			
			CS cs = new CS();
			cs.setCsChatroom(_csChatroom.get());
			cs.setSender(sender);
			cs.setContent(msg.getContent());
			cs.setReceiver(null);
			cs.setIsEdited(0);
			cs.setIsDeleted(0);
			cs.setIsRead(0);

			this.csRepository.save(cs);

			System.out.println("<<< CSService.create 저장 완료 >>>");
		} catch (Exception e) {
			System.out.println(">>> 예외 발생 <<<");
			e.printStackTrace();
		}
	}

    // 🌿 1:1 고객센터 채팅방 채팅 불러오기 메서드
	public List<CS> getCsMessage(Integer csChatroomId) {
		List<CS> csMessages = this.csRepository.findByCsChatroomIdOrdered(csChatroomId);
		
		for(int i=0; i<csMessages.size();i++ ) {
			System.out.println(csMessages.get(i).getContent());
		}
		System.out.println("<<< CSRepository.getCsMessage >>> : 문의사항 메시지 전체 검색");
		return csMessages;
	}
	
	// 🌿 고객센터 채팅방 채팅 읽음 처리 메서드
	public void markMessagesAsRead(Integer csChatroomId, Integer userId) {
        csRepository.markAsReadByChatroomId(csChatroomId, userId);
    }
	
	// 🌿 고객센터 1 : 1 마지막 채팅 내용
	public List<CS> findLatestMessagesPerChatroom() {
	    return csRepository.findLatestMessagesPerChatroom();
	}
}
