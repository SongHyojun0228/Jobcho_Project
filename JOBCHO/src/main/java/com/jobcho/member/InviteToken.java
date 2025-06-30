package com.jobcho.member;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteToken {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_invite_token")
	@SequenceGenerator(name = "seq_invite_token", sequenceName = "SEQ_INVITE_TOKEN", allocationSize = 1)
    private Integer tokenId;

    @Column
    private String token;

    @Column
    private Integer workspaceId;

    @Column
    private String inviteEmail;

    @Column
    private LocalDateTime expiredAt;

}

