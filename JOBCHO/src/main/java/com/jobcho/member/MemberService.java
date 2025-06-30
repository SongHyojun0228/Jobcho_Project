package com.jobcho.member;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jobcho.user.UserRepository;
import com.jobcho.user.Users;
import com.jobcho.workspace.WorkspaceRepository;
import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final WorkspaceRepository workspaceRepository;
	private final UserRepository userRepository;

    // 🌿 각 유저의 워크스페이스 찾기 메서드
	public List<Workspaces> findWorkspacesByUserUserId(Integer userId) {
		return memberRepository.findWorkspacesByUserUserId(userId);
	}

    // 🌿 워크스페이스 멤버 추가 메서드
	public void createMember(Users user, Workspaces workspace) {
		Members member = new Members();
		member.setUser(user);
		member.setWorkspace(workspace);
		this.memberRepository.save(member);
	}

    // 🌿 해당 워크스페이스에 해당하는 유저 찾기 메서드
	public List<Users> findUsersByWorkspaceId(Integer workspaceId) {
		return memberRepository.findUsersByWorkspaceId(workspaceId);
	}

    // 🌿 워크스페이스 멤버 추가 메서드
	public void addMember(Integer workspaceId, String email) {
	    Workspaces workspace = workspaceRepository.findById(workspaceId)
	        .orElseThrow(() -> new IllegalArgumentException("워크스페이스 없음"));
	    System.out.println("워크스페이스 이름 : " + workspace.getWorkspaceName());
	    Users user = userRepository.findByUserEmail(email)
	        .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
	    System.out.println("유저 이메일, 이름 : " + user.getUserEmail() + ", " +  user.getUserName());
	    
	    if (!memberRepository.existsByWorkspaceAndUser(workspace, user)) {
	    	System.out.println("멤버 추가 : " + user.getUserEmail() + ", " + user.getUserName());
	        Members member = new Members();
	        member.setWorkspace(workspace);
	        member.setUser(user);
	        memberRepository.save(member);
	    }
	    else {
	    	System.out.println("해당 유저는 해당 워크 스페이스에 이미 존재");
	    }
	}
}
