package com.jobcho.message;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private Integer messageId;
    private Integer chatroomId;
    private Integer senderId;
    private String content;
    private LocalDateTime createdDate;
    private Integer isEdited;
    private Integer isDeleted;
    private String senderName;
    private String senderImg;
    private String fileName;

    private List<String> mentions; 
}