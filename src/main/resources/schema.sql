CREATE TABLE IF NOT EXISTS ChatRoom
(
    chatroom_id    bigint AUTO_INCREMENT
        PRIMARY KEY,
    chatroom_limit int                                 NOT NULL,
    chatroom_name  varchar(20)                         NOT NULL,
    createdAt      timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt     timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Members
(
    member_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    username    varchar(30)                         NOT NULL,
    nickname    varchar(30)                         NOT NULL,
    email       varchar(100)                        NOT NULL,
    password    varchar(255)                        NOT NULL,
    profile_img varchar(255)                        NULL,
    createdAt   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Chat
(
    chat_id     bigint AUTO_INCREMENT
        PRIMARY KEY,
    message     text                                NOT NULL,
    send_time   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    chatroom_id bigint                              NOT NULL,
    member_id   bigint                              NOT NULL,
    createdAt   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_ChatRoom_TO_Chat_1
        FOREIGN KEY (chatroom_id) REFERENCES ChatRoom (chatroom_id),
    CONSTRAINT FK_Members_TO_Chat_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
);

CREATE TABLE IF NOT EXISTS Posts
(
    post_id    bigint AUTO_INCREMENT
        PRIMARY KEY,
    contents   text                                NOT NULL,
    member_id  bigint                              NOT NULL,
    createdAt  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Members_TO_Posts_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
);

CREATE TABLE IF NOT EXISTS Comments
(
    comment_id bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id    bigint                              NOT NULL,
    member_id  bigint                              NOT NULL,
    parents_id bigint                              NULL,
    contents   text                                NOT NULL,
    createdAt  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Comments_TO_Comments_1
        FOREIGN KEY (parents_id) REFERENCES Comments (comment_id),
    CONSTRAINT FK_Members_TO_Comments_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id),
    CONSTRAINT FK_Posts_TO_Comments_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id)
);

CREATE TABLE IF NOT EXISTS Images
(
    image_id      bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id       bigint                              NOT NULL,
    original_name varchar(255)                        NOT NULL,
    stored_name   varchar(255)                        NOT NULL,
    image_url     varchar(255)                        NOT NULL,
    createdAt     timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Posts_TO_Images_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id)
);

CREATE TABLE IF NOT EXISTS Likes
(
    like_id    bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id    bigint                              NOT NULL,
    member_id  bigint                              NOT NULL,
    createdAt  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT idx_member_post_unique
        UNIQUE (member_id, post_id),
    CONSTRAINT FK_Members_TO_Likes_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id),
    CONSTRAINT FK_Posts_TO_Likes_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id)
);

CREATE TABLE IF NOT EXISTS TimeTables
(
    timetable_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    timetable_name varchar(20)                         NULL,
    date           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    member_id      bigint                              NOT NULL,
    createdAt      timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt     timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Members_TO_TimeTables_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
);

CREATE TABLE IF NOT EXISTS Schedules
(
    schedule_id  bigint AUTO_INCREMENT
        PRIMARY KEY,
    name         varchar(20)                         NOT NULL,
    category     varchar(10)                         NOT NULL,
    x_coordinate double                              NOT NULL,
    y_coordinate double                              NOT NULL,
    start_time   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_time     timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    color_code   char(7)   DEFAULT '#000000'         NOT NULL,
    timetable_id bigint                              NOT NULL,
    createdAt    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedAt   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_TimeTables_TO_Schedules_1
        FOREIGN KEY (timetable_id) REFERENCES TimeTables (timetable_id)
);

