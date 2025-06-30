package com.jobcho.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		System.out.println("UserDetails loadUserByUsername(String userEmail) 호출 : " + userEmail);
		Optional<Users> _user = this.userRepository.findByUserEmail(userEmail);
		
		if(_user.isEmpty()) {
			throw new UsernameNotFoundException("해당 유저는 존재하지 않습니다.");
		}
		
		Users user = _user.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		System.out.println("로그인 성공 >> 유저 정보");
		System.out.println("이름 : " + user.getUserName());
		System.out.println("이메일 : " + user.getUserEmail());
		System.out.println("비밀번호 : " + user.getUserPassword());
		
		if(1 == user.getIsAdmin()) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		return new User(user.getUserEmail(), user.getUserPassword(), authorities);
	}
}
