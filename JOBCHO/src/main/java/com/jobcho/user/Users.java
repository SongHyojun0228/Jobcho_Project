package com.jobcho.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.jobcho.member.Members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	@SequenceGenerator(name = "seq_user", sequenceName = "SEQ_USER", allocationSize = 1)
	private Integer userId;

	@Column
	private String userName;

	@Email
	@Column(unique = true)
	private String userEmail;

	@Column
	private String userPassword;

	@Column(name = "created_date", insertable = false)
	private LocalDateTime createdDate;

	@Column
	private String userImg;

	@Column
	@ColumnDefault("0")
	private Integer isActive;

	@Column
	@ColumnDefault("0")
	private Integer isAdmin;

	@OneToMany(mappedBy = "user")
	private List<Members> members = new ArrayList<>();

}
