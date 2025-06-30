package com.jobcho.git;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GitFile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_git_file")
	@SequenceGenerator(name = "seq_git_file", sequenceName = "SEQ_GIT_FILE", allocationSize = 1)
	private Integer fileId;

	@ManyToOne
	@JoinColumn(name = "commit_id")
	private Commit commit;

	@OneToOne
	@JoinColumn(name = "folder_id")
	private GitFolder gitFolder;

	@Column
	private String filePath;

	@Column
	private String fileName;

}
