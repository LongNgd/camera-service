package vn.atdigital.cameraservice.common;

public interface Constants {
    final class TABLE_STATUS {
        public static final Long PENDING = -1L;
        public static final Long INACTIVE = 0L;
        public static final Long INITIATED = 1L;
        public static final Long ACTIVE = 2L;
        public static final Long UPDATING = -3L;
        public static final Long CANCELED = -2L;
    }

    final class API_RESPONSE {
        private API_RESPONSE() {
            throw new IllegalStateException();
        }

        public static final String RETURN_CODE_OK = "200";
        public static final String RETURN_CODE_CREATED = "201";
        public static final String RETURN_CODE_NO_CONTENT = "204";
        public static final String RETURN_CODE_BAD_REQUEST = "400";
        public static final String RETURN_CODE_ERROR_NOTFOUND = "404";
    }

    final class STATUS_COMMON {
        public static final Boolean RESPONSE_STATUS_TRUE = true;
        public static final Boolean RESPONSE_STATUS_FALSE = false;
    }

    final class LOOKUP_VALUE_CODE {
        public static final String IP_VERSION_IPV4_CODE = "IPV4";
        public static final String IP_VERSION_IPV6_CODE = "IPV6";
        public static final String VIDEO_STREAM_MAIN_CODE = "MAIN_STREAM";
        public static final String VIDEO_STREAM_SUB_CODE = "SUB_STREAM";
    }

    final class ACTION_CODE {
        public static final String INIT_CAMERA = "INIT_CAMERA";
    }

    final class PK_TYPE {
        public static final String CAMERA_TYPE = "Camera";
    }

    final class TABLE_NAME {
        public static final String CAMERA_TABLE = "camera";
        public static final String USER_CAMERA_TABLE = "user_camera";
        public static final String CAMERA_TCP_IP_TABLE = "camera_tcp_ip";
        public static final String TCP_IP_V4_TABLE = "tcp_ip_v4";
        public static final String TCP_IP_V6_TABLE = "tcp_ip_v6";
        public static final String CAMERA_PORT_TABLE = "camera_port";
        public static final String CAMERA_VIDEO_STREAM_TABLE = "camera_video_stream";
        public static final String CAMERA_PTZ_SETTINGS_TABLE = "camera_ptz_settings";
        public static final String CAMERA_AUDIO_TABLE = "camera_audio";
        public static final String CAMERA_AUDIO_STREAM_TABLE = "camera_audio_stream";
        public static final String CAMERA_CONDITION_TABLE = "camera_condition";
        public static final String CAMERA_CONDITION_BASIC_TABLE = "camera_condition_basic";
        public static final String CAMERA_CONDITION_IMAGE_TABLE = "camera_condition_image";
        public static final String CAMERA_CONDITION_AGC_TABLE = "camera_condition_agc";
        public static final String CAMERA_CONDITION_FFC_TABLE = "camera_condition_ffc";
    }
}

