CREATE TABLE ALARMS (
    alarm_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    workspace_id NUMBER,
    notification_id NUMBER,
    task_id NUMBER,
    mention_id NUMBER,
    vote_id NUMBER,
    schedule_id NUMBER,
    is_read NUMBER,
    created_date DATE DEFAULT sysdate,
    
    CONSTRAINT alarm_user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE SET NULL
);

CREATE TABLE CS (
    cs_id NUMBER PRIMARY KEY,
    sender_id NUMBER,
    receiver_id NUMBER,
    content VARCHAR2(4000),
    sent_date DATE DEFAULT sysdate,
    cs_chatroom_id NUMBER,
    is_read NUMBER,
    is_edited NUMBER,
    is_deleted NUMBER,
    sender_name VARCHAR2(100),
    
    CONSTRAINT cs_sender_fk
        FOREIGN KEY (sender_id)
        REFERENCES users(user_id)
        ON DELETE SET NULL,
    
    CONSTRAINT cs_receiver_fk
        FOREIGN KEY (receiver_id)
        REFERENCES users(user_id)
        ON DELETE SET NULL,
      
    CONSTRAINT cs_cs_chatroom_fk
        FOREIGN KEY (cs_chatroom_id)
        REFERENCES cs_chatroom(cs_chatroom_id)
        ON DELETE SET NULL
);

CREATE TABLE FOLDERS (
    folder_id NUMBER PRIMARY KEY,
    workspace_id NUMBER,
    folder_name VARCHAR2(100),
    created_by NUMBER,
    
    CONSTRAINT folder_user_fk
        FOREIGN KEY (created_by)
        REFERENCES users(user_id)
        ON DELETE SET NULL
);

CREATE TABLE FILES (
    file_id NUMBER PRIMARY KEY,
    message_id NUMBER,
    sender_id NUMBER,
    chatroom_id NUMBER,
    my_chatroom_id NUMBER,
    notification_id NUMBER,
    task_id NUMBER,
    file_name VARCHAR2(300),
    uploaded_date DATE DEFAULT sysdate,
    
    CONSTRAINT file_message_fk
        FOREIGN KEY (message_id)
        REFERENCES MESSAGES(message_id)
        ON DELETE SET NULL,
        
    CONSTRAINT folder_sender_fk
        FOREIGN KEY (sender_id)
        REFERENCES users(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT folder_chatroom_fk
        FOREIGN KEY (chatroom_id)
        REFERENCES CHATROOMS(chatroom_id)
        ON DELETE SET NULL,
        
    CONSTRAINT folder_my_chatroom_fk
        FOREIGN KEY (my_chatroom_id)
        REFERENCES MY_CHATROOM(my_chatroom_id)
        ON DELETE SET NULL,
    
    CONSTRAINT folder_notification_fk
        FOREIGN KEY (notification_id)
        REFERENCES notifications(notification_id)
        ON DELETE SET NULL,
        
    CONSTRAINT folder_task_fk
        FOREIGN KEY (task_id)
        REFERENCES TASKS(task_id)
        ON DELETE SET NULL
);

CREATE TABLE VOTES (
    vote_id NUMBER PRIMARY KEY,
    author_id NUMBER,
    chatroom_id NUMBER,
    vote_title VARCHAR2(100),
    start_date DATE,
    end_date DATE,
    
    CONSTRAINT vote_author_fk
        FOREIGN KEY (author_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT vote_chatroom_fk
        FOREIGN KEY (chatroom_id)
        REFERENCES chatrooms(chatroom_id)
        ON DELETE SET NULL
    
);

CREATE TABLE BOOKMARKS (
    bookmark_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    message_id NUMBER,
    notification_id NUMBER,
    task_id NUMBER,
    vote_id NUMBER,
    file_id NUMBER,
    chatroom_id NUMBER,
    my_chatroom_id NUMBER,
    
    CONSTRAINT bookmark_user_fk
        FOREIGN KEY (user_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
    CONSTRAINT bookmark_message_fk
        FOREIGN KEY (message_id)
        REFERENCES MESSAGES(message_id)
        ON DELETE SET NULL,
    CONSTRAINT bookmark_notification_fk
        FOREIGN KEY (notification_id)
        REFERENCES NOTIFICATIONS(notification_id)
        ON DELETE SET NULL,
    CONSTRAINT bookmark_task_fk
        FOREIGN KEY (task_id)
        REFERENCES tasks(task_id)
        ON DELETE SET NULL,
    CONSTRAINT bookmark_vote_fk
        FOREIGN KEY (vote_id)
        REFERENCES votes(vote_id)
        ON DELETE SET NULL,
    CONSTRAINT bookmark_file_fk
        FOREIGN KEY (file_id)
        REFERENCES files(file_id)
        ON DELETE SET NULL,
    CONSTRAINT bookmark_chatroom_fk
        FOREIGN KEY (chatroom_id)
        REFERENCES chatrooms(chatroom_id)
        ON DELETE SET NULL,
    CONSTRAINT bookmark_my_chatroom_fk
        FOREIGN KEY (my_chatroom_id)
        REFERENCES my_chatroom(my_chatroom_id)
        ON DELETE SET NULL
    
);

CREATE TABLE TASKS (
    task_id NUMBER PRIMARY KEY,
    author_id NUMBER,
    chatroom_id NUMBER,
    my_chatroom_id NUMBER,
    task_title VARCHAR2(300),
    description VARCHAR2(500),
    staus NUMBER,
    created_date DATE DEFAULT sysdate,
    start_date DATE,
    end_date DATE,
  
    CONSTRAINT task_author_fk
        FOREIGN KEY (author_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT task_chatroom_fk
        FOREIGN KEY (chatroom_id)
        REFERENCES CHATROOMS(chatroom_id)
        ON DELETE SET NULL,
    
    CONSTRAINT task_my_chatroom_fk
        FOREIGN KEY (my_chatroom_id)
        REFERENCES MY_CHATROOM(my_chatroom_id)
        ON DELETE SET NULL 
);

CREATE TABLE NOTIFICATIONS (
    notification_id NUMBER PRIMARY KEY,
    author_id NUMBER,
    chatroom_id NUMBER,
    title VARCHAR2(300),
    content VARCHAR2(300),
    is_edited NUMBER,
    is_deleted NUMBER,
    created_date DATE DEFAULT sysdate,
  
    CONSTRAINT notification_author_fk
        FOREIGN KEY (author_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT notification_chatroom_fk
        FOREIGN KEY (chatroom_id)
        REFERENCES CHATROOMS(chatroom_id)
        ON DELETE SET NULL

);

CREATE TABLE MEMBERS (
    member_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    workspace_id NUMBER,
    
    CONSTRAINT member_user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT member_workspace_fk
        FOREIGN KEY (workspace_id)
        REFERENCES workspaces(workspace_id)
        ON DELETE SET NULL
);

CREATE TABLE CHATROOMS (
    chatroom_id NUMBER PRIMARY KEY,
    folder_id NUMBER,
    created_by NUMBER,
    chatroom_name VARCHAR2(300),
    description VARCHAR2(500),
    is_private NUMBER,
    created_date DATE DEFAULT sysdate,
    
    CONSTRAINT chatroom_folder_fk
        FOREIGN KEY (folder_id)
        REFERENCES folders(folder_id)
        ON DELETE SET NULL,
        
    CONSTRAINT chatroom_user_fk
        FOREIGN KEY (created_by)
        REFERENCES users(user_id)
        ON DELETE SET NULL
);

CREATE TABLE CHATROOM_MEMBER (
    chatroom_member_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    chatroom_id NUMBER,
    
    CONSTRAINT chatroom_member_user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT chatroom_member_chatroom_fk
        FOREIGN KEY (chatroom_id)
        REFERENCES chatrooms(chatroom_id)
        ON DELETE SET NULL
);

CREATE TABLE MESSAGES (
    message_id NUMBER PRIMARY KEY,
    chatroom_id NUMBER,
    sender_id NUMBER,
    content VARCHAR2(500),
    created_date DATE DEFAULT sysdate,
    is_edited NUMBER,
    is_deleted NUMBER,
    parent_id NUMBER,
    
    CONSTRAINT message_chatroom_fk
        FOREIGN KEY (chatroom_id)
        REFERENCES CHATROOMS(chatroom_id)
        ON DELETE SET NULL,
        
    CONSTRAINT message_user_fk
        FOREIGN KEY (sender_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT message_parent_fk
        FOREIGN KEY (parent_id)
        REFERENCES MESSAGES(message_id)
        ON DELETE SET NULL
);

CREATE TABLE MENTIONS (
    mention_id NUMBER PRIMARY KEY,
    chatroom_Id NUMBER,
    sender_id NUMBER,
    receiver_id NUMBER,
    created_date DATE DEFAULT sysdate,
    message_id NUMBER,
    
    CONSTRAINT mention_chatroom_fk
        FOREIGN KEY (chatroom_Id)
        REFERENCES CHATROOMS(chatroom_Id)
        ON DELETE SET NULL,
        
    CONSTRAINT mention_sender_fk
        FOREIGN KEY (sender_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT mention_receiver_fk
        FOREIGN KEY (receiver_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT mention_message_fk
        FOREIGN KEY (message_id)
        REFERENCES MESSAGES(message_id)
        ON DELETE SET NULL
);

CREATE TABLE REPLIES (
    reply_id NUMBER PRIMARY KEY,
    message_id NUMBER,
    author_id NUMBER,
    file_id NUMBER,
    task_id NUMBER,
    vote_id NUMBER,
    content VARCHAR2(500),
    is_edited NUMBER,
    is_deleted NUMBER,
    mymessage_id NUMBER,
    
    CONSTRAINT reply_author_fk
        FOREIGN KEY (author_id)
        REFERENCES USERS(user_id)
        ON DELETE SET NULL,
        
    CONSTRAINT reply_message_fk
        FOREIGN KEY (message_id)
        REFERENCES MESSAGES(message_id)
        ON DELETE SET NULL,
        
    CONSTRAINT reply_task_fk
        FOREIGN KEY (task_id)
        REFERENCES tasks(task_id)
        ON DELETE SET NULL,
        
    CONSTRAINT reply_vote_fk
        FOREIGN KEY (vote_id)
        REFERENCES votes(vote_id)
        ON DELETE SET NULL,
        
    CONSTRAINT reply_file_fk
        FOREIGN KEY (file_id)
        REFERENCES files(file_id)
        ON DELETE SET NULL
);

CREATE TABLE GIT_FILE (
    file_id NUMBER PRIMARY KEY,
    folder_id NUMBER,
    commit_id NUMBER,
    file_name VARCHAR2(300),
    
    CONSTRAINT git_file_folder_fk
        FOREIGN KEY (folder_id)
        REFERENCES GIT_FOLDER(folder_id)
        ON DELETE SET NULL,
    
    CONSTRAINT git_file_commit_fk
        FOREIGN KEY (commit_id)
        REFERENCES COMMIT(commit_id)
        ON DELETE SET NULL
);

CREATE TABLE WORKSPACES (
    workspace_id NUMBER PRIMARY KEY,
    workspace_name VARCHAR2(100),
    workspace_owner_id NUMBER,
    created_date DATE DEFAULT sysdate,
    workspace_img VARCHAR2(300),
    workspace_domain VARCHAR2(300),
    workspace_temp_img VARCHAR2(300),
    
    CONSTRAINT workspace_user_fk
        FOREIGN KEY (workspace_owner_id)
        REFERENCES users(user_id)
        ON DELETE SET NULL
);


CREATE TABLE example_table (
    id INT PRIMARY KEY,                    -- 기본키 제약조건
    username VARCHAR(50) NOT NULL,         -- NOT NULL 제약조건, 최대 길이 50
    email VARCHAR(100) UNIQUE,              -- UNIQUE 제약조건
    age INT CHECK (age >= 0),               -- 체크 제약조건 (나이 0 이상)
    profile_id INT,                         -- 참조키 대상
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 기본값 지정
    CONSTRAINT fk_profile
        FOREIGN KEY (profile_id)
        REFERENCES profiles(id)
        ON DELETE SET NULL                 -- 부모 삭제 시 이 컬럼을 NULL로 설정
);
