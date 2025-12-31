-- liquibase formatted sql

-- changeset longn:029
ALTER TABLE "camera"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:030
ALTER TABLE "camera_condition"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:031
ALTER TABLE "camera_video_stream"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:032
ALTER TABLE "camera_audio"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:033
ALTER TABLE "camera_audio_stream"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:034
ALTER TABLE "camera_tcp_ip"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:035
ALTER TABLE "tcp_ip_v4"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:036
ALTER TABLE "tcp_ip_v6"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:037
ALTER TABLE "camera_port"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:038
ALTER TABLE "camera_ptz_settings"
ALTER COLUMN "status" TYPE int8
USING status::bigint;
