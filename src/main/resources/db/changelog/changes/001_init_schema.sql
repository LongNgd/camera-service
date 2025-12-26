-- liquibase formatted sql

-- changeset longnd:001
CREATE TABLE "action_audit" (
                                "id" int8 PRIMARY KEY,
                                "action_code" varchar,
                                "description" varchar,
                                "issue_datetime" timestamp(6),
                                "pk_id" int8,
                                "pk_type" varchar,
                                "step" varchar,
                                "username" varchar
);

-- changeset longnd:002
CREATE TABLE "action_detail" (
                                 "id" int8 PRIMARY KEY,
                                 "audit_id" int8,
                                 "issue_datetime" timestamp(6),
                                 "new_value" json,
                                 "old_value" json,
                                 "pk_id" int8,
                                 "table_name" varchar
);

CREATE TABLE "group" (
                         "id" int8 PRIMARY KEY,
                         "name" varchar,
                         "description" varchar,
                         "created_user" varchar,
                         "created_datetime" timestamp(6),
                         "updated_user" varchar,
                         "updated_datetime" timestamp(6),
                         "status" varchar
);

-- changeset longnd:003
CREATE TABLE "authority" (
                             "id" int8 PRIMARY KEY,
                             "name" varchar,
                             "description" varchar,
                             "created_user" varchar,
                             "created_datetime" timestamp(6),
                             "updated_user" varchar,
                             "updated_datetime" timestamp(6),
                             "status" varchar
);

-- changeset longnd:004
CREATE TABLE "group_authority" (
                                   "group_id" int8,
                                   "authority_id" int8,
                                   PRIMARY KEY ("group_id", "authority_id")
);

-- changeset longnd:005
CREATE TABLE "user" (
                        "id" int8 PRIMARY KEY,
                        "group_id" int8,
                        "camera_id" int8,
                        "name" varchar,
                        "username" varchar,
                        "password" varchar,
                        "description" varchar,
                        "created_user" varchar,
                        "created_datetime" timestamp(6),
                        "updated_user" varchar,
                        "updated_datetime" timestamp(6),
                        "status" varchar
);

-- changeset longnd:006
CREATE TABLE "camera" (
                          "id" int8 PRIMARY KEY,
                          "owner_id" int8,
                          "owner_type" varchar,
                          "name" varchar,
                          "language_code" varchar,
                          "video_standard_code" varchar,
                          "created_user" varchar,
                          "created_datetime" timestamp(6),
                          "updated_user" varchar,
                          "updated_datetime" timestamp(6),
                          "status" varchar
);

-- changeset longnd:007
CREATE TABLE "camera_condition" (
                                    "id" int8 PRIMARY KEY,
                                    "camera_id" int8,
                                    "profile_code" varchar,
                                    "colorization_code" varchar,
                                    "created_user" varchar,
                                    "created_datetime" timestamp(6),
                                    "updated_user" varchar,
                                    "updated_datetime" timestamp(6),
                                    "status" varchar
);

-- changeset longnd:008
CREATE TABLE "camera_condition_basic" (
                                          "id" int8 PRIMARY KEY,
                                          "camera_id" int8,
                                          "brightness" integer,
                                          "contrast" integer,
                                          "sharpness" integer,
                                          "detail_enhancement" integer,
                                          "histogram_equalization" integer,
                                          "ezoom" integer,
                                          "roi_type_code" varchar,
                                          "mirror_code" varchar,
                                          "flip_code" varchar,
                                          "created_user" varchar,
                                          "created_datetime" timestamp(6),
                                          "updated_user" varchar,
                                          "updated_datetime" timestamp(6),
                                          "status" varchar
);

-- changeset longnd:009
CREATE TABLE "camera_condition_image" (
                                          "id" int8 PRIMARY KEY,
                                          "camera_id" int8,
                                          "basic_nr" integer,
                                          "front_module" integer,
                                          "rear_chip" integer,
                                          "created_user" varchar,
                                          "created_datetime" timestamp(6),
                                          "updated_user" varchar,
                                          "updated_datetime" timestamp(6),
                                          "status" varchar
);

-- changeset longnd:010
CREATE TABLE "camera_condition_agc" (
                                        "id" int8 PRIMARY KEY,
                                        "camera_id" int8,
                                        "gain_mode" integer,
                                        "agc_plateau" integer,
                                        "gain_mode_code" varchar,
                                        "created_user" varchar,
                                        "created_datetime" timestamp(6),
                                        "updated_user" varchar,
                                        "updated_datetime" timestamp(6),
                                        "status" varchar
);

-- changeset longnd:011
CREATE TABLE "camera_condition_ffc" (
                                        "id" int8 PRIMARY KEY,
                                        "camera_id" int8,
                                        "ffc_mode_code" varchar,
                                        "ffc_period" integer,
                                        "created_user" varchar,
                                        "created_datetime" timestamp(6),
                                        "updated_user" varchar,
                                        "updated_datetime" timestamp(6),
                                        "status" varchar
);

-- changeset longnd:012
CREATE TABLE "camera_video_stream" (
                                       "id" int8 PRIMARY KEY,
                                       "camera_id" int8,
                                       "encode_mode_code" varchar,
                                       "resolution_code" varchar,
                                       "frame_rate_code" varchar,
                                       "bit_rate_type_code" varchar,
                                       "reference_bit_rate_code" varchar,
                                       "bit_rate_code" varchar,
                                       "i_frame_interval" integer,
                                       "svc_code" varchar,
                                       "created_user" varchar,
                                       "created_datetime" timestamp(6),
                                       "updated_user" varchar,
                                       "updated_datetime" timestamp(6),
                                       "status" varchar
);

-- changeset longnd:013
CREATE TABLE "camera_video_stream_main" (
                                            "id" int8 PRIMARY KEY,
                                            "has_watermark_settings" boolean,
                                            "watermark_character" varchar
);

-- changeset longnd:014
CREATE TABLE "camera_video_stream_sub" (
                                           "id" int8 PRIMARY KEY,
                                           "is_enabled" boolean
);

-- changeset longnd:015
CREATE TABLE "camera_audio" (
                                "id" int8 PRIMARY KEY,
                                "camera_id" int8,
                                "audioIn_type_code" varchar,
                                "noise_filter_code" varchar,
                                "microphone_volume" integer,
                                "speaker_volume" integer,
                                "created_user" varchar,
                                "created_datetime" timestamp(6),
                                "updated_user" varchar,
                                "updated_datetime" timestamp(6),
                                "status" varchar
);

-- changeset longnd:016
CREATE TABLE "camera_audio_stream" (
                                       "id" int8 PRIMARY KEY,
                                       "camera_audio_id" int8,
                                       "type_code" varchar,
                                       "is_enabled" boolean,
                                       "encode_mode_code" varchar,
                                       "sampling_frequency_code" varchar,
                                       "created_user" varchar,
                                       "created_datetime" timestamp(6),
                                       "updated_user" varchar,
                                       "updated_datetime" timestamp(6),
                                       "status" varchar
);

-- changeset longnd:017
CREATE TABLE "camera_tcp_ip" (
                                 "id" int8 PRIMARY KEY,
                                 "camera_id" int8,
                                 "host_name" varchar,
                                 "ethernet_card_code" varchar,
                                 "mode_code" varchar,
                                 "mac_address" varchar,
                                 "ip_version_code" varchar,
                                 "ip_address" varchar,
                                 "default_gateway" varchar,
                                 "preferred_dns" varchar,
                                 "alternate_dns" varchar,
                                 "is_enable_set" boolean,
                                 "created_user" varchar,
                                 "created_datetime" timestamp(6),
                                 "updated_user" varchar,
                                 "updated_datetime" timestamp(6),
                                 "status" varchar
);

-- changeset longnd:018
CREATE TABLE "tcp_ip_v4" (
                             "id" int8 PRIMARY KEY,
                             "camera_tcp_id" int8,
                             "subnet_mask" varchar,
                             "created_user" varchar,
                             "created_datetime" timestamp(6),
                             "updated_user" varchar,
                             "updated_datetime" timestamp(6),
                             "status" varchar
);

-- changeset longnd:019
CREATE TABLE "tcp_ip_v6" (
                             "id" int8 PRIMARY KEY,
                             "camera_tcp_id" int8,
                             "link_address" varchar,
                             "cidr_notation" varchar,
                             "created_user" varchar,
                             "created_datetime" timestamp(6),
                             "updated_user" varchar,
                             "updated_datetime" timestamp(6),
                             "status" varchar
);

-- changeset longnd:020
CREATE TABLE "camera_port" (
                               "id" int8 PRIMARY KEY,
                               "camera_id" int8,
                               "max_connection" integer,
                               "tcp_port" integer,
                               "udp_port" integer,
                               "http_port" integer,
                               "rtsp_port" integer,
                               "https_port" integer,
                               "created_user" varchar,
                               "created_datetime" timestamp(6),
                               "updated_user" varchar,
                               "updated_datetime" timestamp(6),
                               "status" varchar
);

-- changeset longnd:021
CREATE TABLE "camera_ptz_settings" (
                                       "id" int8 PRIMARY KEY,
                                       "camera_id" int8,
                                       "protocol_code" varchar,
                                       "address" varchar,
                                       "baud_rate_code" varchar,
                                       "data_bit_code" varchar,
                                       "stop_bit_code" varchar,
                                       "parity_code" varchar,
                                       "created_user" varchar,
                                       "created_datetime" timestamp(6),
                                       "updated_user" varchar,
                                       "updated_datetime" timestamp(6),
                                       "status" varchar
);

-- changeset longnd:022
ALTER TABLE "action_detail" ADD FOREIGN KEY ("audit_id") REFERENCES "action_audit" ("id");

-- changeset longnd:023
ALTER TABLE "user" ADD FOREIGN KEY ("group_id") REFERENCES "group" ("id");
-- changeset longnd:024
ALTER TABLE "user" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");

-- changeset longnd:025
ALTER TABLE "group_authority" ADD FOREIGN KEY ("group_id") REFERENCES "group" ("id");
-- changeset longnd:026
ALTER TABLE "group_authority" ADD FOREIGN KEY ("authority_id") REFERENCES "authority" ("id");

-- changeset longnd:027
ALTER TABLE "camera_condition" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:028
ALTER TABLE "camera_condition_basic" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:029
ALTER TABLE "camera_condition_image" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:030
ALTER TABLE "camera_condition_agc" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:031
ALTER TABLE "camera_condition_ffc" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");

-- changeset longnd:032
ALTER TABLE "camera_video_stream" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:033
ALTER TABLE "camera_video_stream_main" ADD FOREIGN KEY ("id") REFERENCES "camera_video_stream" ("id");
-- changeset longnd:034
ALTER TABLE "camera_video_stream_sub" ADD FOREIGN KEY ("id") REFERENCES "camera_video_stream" ("id");

-- changeset longnd:035
ALTER TABLE "camera_audio" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:036
ALTER TABLE "camera_audio_stream" ADD FOREIGN KEY ("camera_audio_id") REFERENCES "camera_audio" ("id");

-- changeset longnd:037
ALTER TABLE "camera_tcp_ip" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:038
ALTER TABLE "tcp_ip_v4" ADD FOREIGN KEY ("camera_tcp_id") REFERENCES "camera_tcp_ip" ("id");
-- changeset longnd:039
ALTER TABLE "tcp_ip_v6" ADD FOREIGN KEY ("camera_tcp_id") REFERENCES "camera_tcp_ip" ("id");

-- changeset longnd:040
ALTER TABLE "camera_port" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");
-- changeset longnd:041
ALTER TABLE "camera_ptz_settings" ADD FOREIGN KEY ("camera_id") REFERENCES "camera" ("id");