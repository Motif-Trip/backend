DROP TABLE IF EXISTS `Members`;

CREATE TABLE `Members`
(
    `member_id`   BIGINT       NOT NULL,
    `username`    VARCHAR(20)  NOT NULL,
    `nickname`    VARCHAR(20)  NOT NULL,
    `email`       VARCHAR(60)  NOT NULL,
    `password`    VARCHAR(30)  NOT NULL,
    `profile_img` VARCHAR(255) NULL
);

DROP TABLE IF EXISTS `Schedules`;

CREATE TABLE `Schedules`
(
    `schedule_id`  BIGINT      NOT NULL,
    `name`         VARCHAR(20) NOT NULL,
    `category`     VARCHAR(10) NOT NULL,
    `x_coordinate` DOUBLE      NOT NULL,
    `y_coordinate` DOUBLE      NOT NULL,
    `start_time`   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `end_time`     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `color_code`   CHAR(7)     NOT NULL DEFAULT #000000,
                                            `table_id` BIGINT NOT NULL
);

DROP TABLE IF EXISTS `TimeTables`;

CREATE TABLE `TimeTables`
(
    `table_id`   BIGINT      NOT NULL,
    `table_name` VARCHAR(20) NULL,
    `date`       DATE        NOT NULL DEFAULT CURRENT_DATE,
    `member_id`  BIGINT      NOT NULL
);

DROP TABLE IF EXISTS `Posts`;

CREATE TABLE `Posts`
(
    `post_id`   BIGINT NOT NULL,
    `contents`  TEXT   NOT NULL,
    `member_id` BIGINT NOT NULL
);

DROP TABLE IF EXISTS `Comments`;

CREATE TABLE `Comments`
(
    `comment_id` BIGINT NOT NULL,
    `post_id`    BIGINT NOT NULL,
    `member_id`  BIGINT NOT NULL,
    `parents_id` BIGINT NULL,
    `contents`   TEXT   NOT NULL
);

DROP TABLE IF EXISTS `Images`;

CREATE TABLE `Images`
(
    `image_id`      BIGINT       NOT NULL,
    `post_id`       BIGINT       NOT NULL,
    `original_name` VARCHAR(255) NOT NULL,
    `stored_name`   VARCHAR(255) NOT NULL,
    `image_url`     VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS `Likes`;

CREATE TABLE `Likes`
(
    `like_id`   BIGINT NOT NULL,
    `post_id`   BIGINT NOT NULL,
    `member_id` BIGINT NOT NULL
);

DROP TABLE IF EXISTS `Chat`;

CREATE TABLE `Chat`
(
    `chat_id`     BIGINT    NOT NULL,
    `message`     TEXT      NOT NULL,
    `send_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `chatroom_id` BIGINT    NOT NULL,
    `member_id`   BIGINT    NOT NULL
);

DROP TABLE IF EXISTS `ChatRoom`;

CREATE TABLE `ChatRoom`
(
    `chatroom_id`    BIGINT      NOT NULL,
    `chatroom_limit` INT         NOT NULL,
    `chatroom_name`  VARCHAR(20) NULL DEFAULT 채팅방 + 채팅방ID
);

ALTER TABLE `Members`
    ADD CONSTRAINT `PK_MEMBERS` PRIMARY KEY (
                                             `member_id`
        );

ALTER TABLE `Schedules`
    ADD CONSTRAINT `PK_SCHEDULES` PRIMARY KEY (
                                               `schedule_id`
        );

ALTER TABLE `TimeTables`
    ADD CONSTRAINT `PK_TIMETABLES` PRIMARY KEY (
                                                `table_id`
        );

ALTER TABLE `Posts`
    ADD CONSTRAINT `PK_POSTS` PRIMARY KEY (
                                           `post_id`
        );

ALTER TABLE `Comments`
    ADD CONSTRAINT `PK_COMMENTS` PRIMARY KEY (
                                              `comment_id`
        );

ALTER TABLE `Images`
    ADD CONSTRAINT `PK_IMAGES` PRIMARY KEY (
                                            `image_id`
        );

ALTER TABLE `Likes`
    ADD CONSTRAINT `PK_LIKES` PRIMARY KEY (
                                           `like_id`
        );

ALTER TABLE `Chat`
    ADD CONSTRAINT `PK_CHAT` PRIMARY KEY (
                                          `chat_id`
        );

ALTER TABLE `ChatRoom`
    ADD CONSTRAINT `PK_CHATROOM` PRIMARY KEY (
                                              `chatroom_id`
        );

ALTER TABLE `Schedules`
    ADD CONSTRAINT `FK_TimeTables_TO_Schedules_1` FOREIGN KEY (
                                                               `table_id`
        )
        REFERENCES `TimeTables` (
                                 `table_id`
            );

ALTER TABLE `TimeTables`
    ADD CONSTRAINT `FK_Members_TO_TimeTables_1` FOREIGN KEY (
                                                             `member_id`
        )
        REFERENCES `Members` (
                              `member_id`
            );

ALTER TABLE `Posts`
    ADD CONSTRAINT `FK_Members_TO_Posts_1` FOREIGN KEY (
                                                        `member_id`
        )
        REFERENCES `Members` (
                              `member_id`
            );

ALTER TABLE `Comments`
    ADD CONSTRAINT `FK_Posts_TO_Comments_1` FOREIGN KEY (
                                                         `post_id`
        )
        REFERENCES `Posts` (
                            `post_id`
            );

ALTER TABLE `Comments`
    ADD CONSTRAINT `FK_Members_TO_Comments_1` FOREIGN KEY (
                                                           `member_id`
        )
        REFERENCES `Members` (
                              `member_id`
            );

ALTER TABLE `Comments`
    ADD CONSTRAINT `FK_Comments_TO_Comments_1` FOREIGN KEY (
                                                            `parents_id`
        )
        REFERENCES `Comments` (
                               `comment_id`
            );

ALTER TABLE `Images`
    ADD CONSTRAINT `FK_Posts_TO_Images_1` FOREIGN KEY (
                                                       `post_id`
        )
        REFERENCES `Posts` (
                            `post_id`
            );

ALTER TABLE `Likes`
    ADD CONSTRAINT `FK_Posts_TO_Likes_1` FOREIGN KEY (
                                                      `post_id`
        )
        REFERENCES `Posts` (
                            `post_id`
            );

ALTER TABLE `Likes`
    ADD CONSTRAINT `FK_Members_TO_Likes_1` FOREIGN KEY (
                                                        `member_id`
        )
        REFERENCES `Members` (
                              `member_id`
            );

ALTER TABLE `Chat`
    ADD CONSTRAINT `FK_ChatRoom_TO_Chat_1` FOREIGN KEY (
                                                        `chatroom_id`
        )
        REFERENCES `ChatRoom` (
                               `chatroom_id`
            );

ALTER TABLE `Chat`
    ADD CONSTRAINT `FK_Members_TO_Chat_1` FOREIGN KEY (
                                                       `member_id`
        )
        REFERENCES `Members` (
                              `member_id`
            );

