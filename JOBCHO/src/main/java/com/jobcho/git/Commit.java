package com.jobcho.git;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Commit {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_commit")
	@SequenceGenerator(name = "seq_commit", sequenceName = "SEQ_COMMIT", allocationSize = 1)
	private Integer commitId;

	@ManyToOne
	@JoinColumn(name = "branch_id")
	private Branch branch;

	@Column
	private String content;

	@Column(name = "uploaded_date", insertable = false)
	private LocalDateTime uploadedDate;

	public String getTimeAgo() {
		LocalDateTime now = LocalDateTime.now();
		long minutes = ChronoUnit.MINUTES.between(uploadedDate, now);
		long hours = ChronoUnit.HOURS.between(uploadedDate, now);
		long days = ChronoUnit.DAYS.between(uploadedDate, now);
		long months = ChronoUnit.MONTHS.between(uploadedDate.withDayOfMonth(1), now.withDayOfMonth(1));

		if (minutes < 1)
			return "방금 전";
		else if (minutes < 60)
			return minutes + "분 전";
		else if (hours < 24)
			return hours + "시간 전";
		else if (days < 30)
			return days + "일 전";
		else
			return months + "달 전";
	}

	public LocalDateTime getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(LocalDateTime uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

}
