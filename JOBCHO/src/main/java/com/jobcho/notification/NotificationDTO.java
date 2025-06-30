package com.jobcho.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    private int chatroomId;
    private int workspaceId;
    private int authorId;
    private String content;
}
