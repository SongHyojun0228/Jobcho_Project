package com.jobcho.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <Users, Integer>{
	Optional<Users> findByUserEmail(String userEmail);

	Users findByUserName(String userName);
}
