CREATE TABLE IF NOT EXISTS Members
(
    member_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(30)                         NOT NULL,
    nickname    VARCHAR(30)                         NOT NULL,
    email       VARCHAR(100)                        NOT NULL UNIQUE,
    password    VARCHAR(255)                        NOT NULL,
    profile_img VARCHAR(255)                        NULL,
    is_deleted  boolean   DEFAULT FALSE             NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX (email)
);

CREATE TABLE IF NOT EXISTS ChatRooms
(
    chatroom_id    bigint AUTO_INCREMENT
        PRIMARY KEY,
    chatroom_limit int                                 NOT NULL,
    chatroom_name  varchar(20)                         NOT NULL,
    is_deleted     boolean   DEFAULT FALSE             NOT NULL,
    created_at     timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS Chats
(
    chat_id     bigint AUTO_INCREMENT
        PRIMARY KEY,
    chatroom_id bigint                              NOT NULL,
    member_id   bigint                              NOT NULL,
    message     text                                NOT NULL,
    is_deleted  boolean   DEFAULT FALSE             NOT NULL,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_ChatRoom_TO_Chat_1
        FOREIGN KEY (chatroom_id) REFERENCES ChatRooms (chatroom_id) ON DELETE CASCADE,
    CONSTRAINT FK_Members_TO_Chat_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Posts
(
    post_id     bigint AUTO_INCREMENT
        PRIMARY KEY,
    member_id   bigint                              NOT NULL,
    contents    text                                NOT NULL,
    is_deleted  boolean   DEFAULT FALSE             NOT NULL,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Members_TO_Posts_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id) ON DELETE CASCADE
);

/*
    게시물 제목 필드 추가.
*/
ALTER TABLE Posts
    ADD title VARCHAR(100) NOT NULL;

CREATE TABLE IF NOT EXISTS Comments
(
    comment_id  bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id     bigint                              NOT NULL,
    member_id   bigint                              NOT NULL,
    parents_id  bigint                              NULL,
    contents    text                                NOT NULL,
    is_deleted  boolean   DEFAULT FALSE             NOT NULL,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Comments_TO_Comments_1
        FOREIGN KEY (parents_id) REFERENCES Comments (comment_id) ON DELETE CASCADE,
    CONSTRAINT FK_Members_TO_Comments_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id) ON DELETE CASCADE,
    CONSTRAINT FK_Posts_TO_Comments_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Images
(
    image_id      bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id       bigint                              NOT NULL,
    original_name varchar(255)                        NOT NULL,
    stored_name   varchar(255)                        NOT NULL,
    image_url     varchar(255)                        NOT NULL,
    is_deleted    boolean   DEFAULT FALSE             NOT NULL,
    created_at    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Posts_TO_Images_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Likes
(
    like_id     bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id     bigint                              NOT NULL,
    member_id   bigint                              NOT NULL,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT idx_member_post_unique
        UNIQUE (member_id, post_id),
    CONSTRAINT FK_Members_TO_Likes_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id) ON DELETE CASCADE,
    CONSTRAINT FK_Posts_TO_Likes_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TimeTables
(
    timetable_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    member_id      bigint                              NOT NULL,
    timetable_name varchar(30)                         NOT NULL,
    date           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_deleted     boolean   DEFAULT FALSE             NOT NULL,
    created_at     timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Members_TO_TimeTables_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Schedules
(
    schedule_id  bigint AUTO_INCREMENT
        PRIMARY KEY,
    timetable_id bigint                              NOT NULL,
    name         varchar(20)                         NOT NULL,
    category     varchar(10)                         NOT NULL,
    latitude     double                              NOT NULL,
    longitude    double                              NOT NULL,
    start_time   timestamp                           NOT NULL,
    end_time     timestamp                           NOT NULL,
    color_code   char(7)   DEFAULT '#000000'         NOT NULL,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_TimeTables_TO_Schedules_1
        FOREIGN KEY (timetable_id) REFERENCES TimeTables (timetable_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS RefreshTokens
(
    token_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    token_value   VARCHAR(255)                        NOT NULL,
    email         VARCHAR(100)                        NOT NULL UNIQUE,
    status        VARCHAR(20)                         NOT NULL DEFAULT 'active',
    expiration_at TIMESTAMP                           NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Members_TO_RefreshTokens_1
        FOREIGN KEY (email) REFERENCES Members (email) ON DELETE CASCADE
);

