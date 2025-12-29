-- liquibase formatted sql

-- changeset longn:037
ALTER TABLE "camera"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:038
ALTER TABLE "camera_condition"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:039
ALTER TABLE "camera_condition_basic"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:040
ALTER TABLE "camera_condition_image"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:041
ALTER TABLE "camera_condition_agc"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:042
ALTER TABLE "camera_condition_ffc"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:043
ALTER TABLE "camera_video_stream"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:044
ALTER TABLE "camera_audio"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:045
ALTER TABLE "camera_audio_stream"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:046
ALTER TABLE "camera_tcp_ip"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:047
ALTER TABLE "tcp_ip_v4"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:048
ALTER TABLE "tcp_ip_v6"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:049
ALTER TABLE "camera_port"
ALTER COLUMN "status" TYPE int8
USING status::bigint;

-- changeset longn:050
ALTER TABLE "camera_ptz_settings"
ALTER COLUMN "status" TYPE int8
USING status::bigint;
