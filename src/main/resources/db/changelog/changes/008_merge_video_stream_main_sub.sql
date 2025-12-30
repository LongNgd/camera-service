-- liquibase formatted sql

-- changeset longnd:053
DROP TABLE IF EXISTS "camera_video_stream_main";

-- changeset longnd:054
DROP TABLE IF EXISTS "camera_video_stream_sub";

-- changeset longnd:055
ALTER TABLE "camera_video_stream"
    ADD COLUMN "has_watermark_settings" boolean,
    ADD COLUMN "watermark_character" varchar,
    ADD COLUMN "type_code" varchar,
    ADD COLUMN "is_enabled" boolean;
