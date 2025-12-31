-- liquibase formatted sql

-- changeset longnd:045
CREATE TABLE user_camera (
                             id                  BIGSERIAL PRIMARY KEY,
                             user_id             INT8,
                             camera_id           INT8 NOT NULL,

                             status              INT8,

                             created_user        VARCHAR(100),
                             created_datetime    TIMESTAMP(6),
                             updated_user        VARCHAR(100),
                             updated_datetime    TIMESTAMP(6),

                             CONSTRAINT fk_user_camera_camera
                                 FOREIGN KEY (camera_id) REFERENCES camera(id) ON DELETE CASCADE,

                             CONSTRAINT uk_user_camera UNIQUE (user_id, camera_id)
);
