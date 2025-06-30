package com.jobcho.task;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private Integer authorId;
    private Integer chatroomId;
    private String taskTitle;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer workspaceId;
}
