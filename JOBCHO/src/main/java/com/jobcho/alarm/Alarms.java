package com.jobcho.alarm;

import java.time.LocalDateTime;

import com.jobcho.mention.Mentions;
import com.jobcho.notification.Notifications;
import com.jobcho.task.Tasks;
import com.jobcho.user.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Alarms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alarm")
	@SequenceGenerator(name = "seq_alarm", sequenceName = "SEQ_ALARM", allocationSize = 1)
	private Integer alarmId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notification_id")
	private Notifications notification;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id")
	private Tasks task;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mention_id")
	private Mentions mention;
	
	@Column(name = "created_date", insertable = false)
	private LocalDateTime createdDate;
	
	@Column
	private Integer workspaceId;
	
	@Column(name = "is_read", insertable = false)
	private Integer isRead;
	
}
