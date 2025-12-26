-- liquibase formatted sql

-- changeset longnd:070
DROP TABLE IF EXISTS "camera_video_stream_main";

-- changeset longnd:071
DROP TABLE IF EXISTS "camera_video_stream_sub";

-- changeset longnd:072
ALTER TABLE "camera_video_stream"
    ADD COLUMN "has_watermark_settings" boolean,
    ADD COLUMN "watermark_character" varchar,
    ADD COLUMN "type_code" varchar,
    ADD COLUMN "is_enabled" boolean;
