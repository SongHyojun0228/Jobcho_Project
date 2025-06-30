package com.jobcho.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteTokenRepository extends JpaRepository<InviteToken, Long> {
	Optional<InviteToken> findByToken(String token);

	void deleteByToken(String token);
}
