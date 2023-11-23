CREATE TABLE Members
(
    member_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    username    varchar(30)                          NOT NULL,
    nickname    varchar(30)                          NOT NULL,
    email       varchar(100)                         NOT NULL,
    password    varchar(255)                         NOT NULL,
    profile_img varchar(255)                         NULL,
    is_deleted  tinyint(1) DEFAULT 0                 NOT NULL,
    created_at  timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT email
        UNIQUE (email)
);


CREATE INDEX email_2
    ON Members (email);


CREATE TABLE ChatRooms
(
    chatroom_id    bigint AUTO_INCREMENT
        PRIMARY KEY,
    chatroom_limit int                                  NOT NULL,
    chatroom_name  varchar(20)                          NOT NULL,
    is_deleted     tinyint(1) DEFAULT 0                 NOT NULL,
    created_at     timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at    timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE Chats
(
    chat_id     bigint AUTO_INCREMENT
        PRIMARY KEY,
    chatroom_id bigint                               NOT NULL,
    member_id   bigint                               NOT NULL,
    message     text                                 NOT NULL,
    is_deleted  tinyint(1) DEFAULT 0                 NOT NULL,
    created_at  timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_ChatRoom_TO_Chat_1
        FOREIGN KEY (chatroom_id) REFERENCES ChatRooms (chatroom_id)
            ON DELETE CASCADE,
    CONSTRAINT FK_Members_TO_Chat_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
            ON DELETE CASCADE
);


CREATE TABLE Posts
(
    post_id     bigint AUTO_INCREMENT
        PRIMARY KEY,
    member_id   bigint                               NOT NULL,
    contents    text                                 NOT NULL,
    is_deleted  tinyint(1) DEFAULT 0                 NOT NULL,
    created_at  timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    title       varchar(100)                         NOT NULL,
    CONSTRAINT FK_Members_TO_Posts_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
            ON DELETE CASCADE
);


CREATE TABLE Comments
(
    comment_id  bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id     bigint                               NOT NULL,
    member_id   bigint                               NOT NULL,
    parents_id  bigint                               NULL,
    contents    text                                 NOT NULL,
    is_deleted  tinyint(1) DEFAULT 0                 NOT NULL,
    created_at  timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Comments_TO_Comments_1
        FOREIGN KEY (parents_id) REFERENCES Comments (comment_id)
            ON DELETE CASCADE,
    CONSTRAINT FK_Members_TO_Comments_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
            ON DELETE CASCADE,
    CONSTRAINT FK_Posts_TO_Comments_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id)
            ON DELETE CASCADE
);


CREATE TABLE Images
(
    image_id      bigint AUTO_INCREMENT
        PRIMARY KEY,
    post_id       bigint                               NOT NULL,
    original_name varchar(255)                         NOT NULL,
    stored_name   varchar(255)                         NOT NULL,
    image_url     varchar(255)                         NOT NULL,
    is_deleted    tinyint(1) DEFAULT 0                 NOT NULL,
    created_at    timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at   timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Posts_TO_Images_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id)
            ON DELETE CASCADE
);


CREATE TABLE Likes
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
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
            ON DELETE CASCADE,
    CONSTRAINT FK_Posts_TO_Likes_1
        FOREIGN KEY (post_id) REFERENCES Posts (post_id)
            ON DELETE CASCADE
);


CREATE TABLE TimeTables
(
    timetable_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    member_id      bigint                               NOT NULL,
    timetable_name varchar(30)                          NOT NULL,
    schedule_date  timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_deleted     tinyint(1) DEFAULT 0                 NOT NULL,
    created_at     timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at    timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_Members_TO_TimeTables_1
        FOREIGN KEY (member_id) REFERENCES Members (member_id)
            ON DELETE CASCADE
);


CREATE TABLE Schedules
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
        FOREIGN KEY (timetable_id) REFERENCES TimeTables (timetable_id)
            ON DELETE CASCADE
);


CREATE TABLE RefreshTokens
(
    token_id      bigint AUTO_INCREMENT
        PRIMARY KEY,
    token_value   varchar(255)                          NOT NULL,
    email         varchar(100)                          NOT NULL,
    status        varchar(20) DEFAULT 'active'          NOT NULL,
    expiration_at timestamp                             NOT NULL,
    created_at    timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at   timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT email
        UNIQUE (email),
    CONSTRAINT FK_Members_TO_RefreshTokens_1
        FOREIGN KEY (email) REFERENCES Members (email)
            ON DELETE CASCADE
);


DROP TABLE IF EXISTS `ProfileImages`;
CREATE TABLE `ProfileImages`
(
    `profile_image_id` BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `member_id`        BIGINT       NOT NULL,
    `original_name`    VARCHAR(255) NOT NULL,
    `stored_name`      VARCHAR(255) NOT NULL,
    `image_url`        VARCHAR(255) NOT NULL,
    is_deleted         tinyint(1)   NOT NULL DEFAULT 0,
    `created_at`       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES `Members` (member_id)
);

ALTER TABLE TimeTables
    ADD COLUMN email VARCHAR(255);

ALTER TABLE TimeTables
    ADD CONSTRAINT fk_member_email
        FOREIGN KEY (email)
            REFERENCES Members(email);

