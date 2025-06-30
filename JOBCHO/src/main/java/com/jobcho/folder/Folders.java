package com.jobcho.folder;


import java.util.List;

import com.jobcho.chatroom.Chatrooms;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Folders {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_folder")
	@SequenceGenerator(name = "seq_folder", sequenceName = "SEQ_FOLDER", allocationSize = 1)
	private Integer folderId;
	
	@Column
	private Integer workspaceId;
	
	@Column
	private String folderName;
	
	@Column
	private Integer createdBy;
	
	@Transient
	private List<Chatrooms> chatrooms;
	
}
