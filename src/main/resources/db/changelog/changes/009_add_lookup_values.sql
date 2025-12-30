-- liquibase formatted sql

-- changeset longnd:052
INSERT INTO lookup_type (code, name)
VALUES (
           'CONDITION_PROFILE',
           'Condition profile'
       );

-- changeset longnd:053
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_PROFILE'),'PROFILE_GENERAL','General',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_PROFILE'),'PROFILE_DAY','Day',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_PROFILE'),'PROFILE_NIGHT','Day',TRUE);

-- changeset longnd:054
INSERT INTO lookup_type (code, name)
VALUES (
           'CONDITION_COLORIZATION',
           'Condition colorization'
       );

-- changeset longnd:055
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_WHITE_HOT','White Hot',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_BLACK_HOT','Black Hot',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_FUSION','Fusion',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_RAINBOW','Rainbow',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_GLOBOW','Globow',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_IRONBOW_1','Ironbow1',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_IRONBOW_2','Ironbow2',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_SEPIA','Sepia',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_COLOR_1','Color1',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_COLOR_2','Color2',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_ICEFIRE','IceFire',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_RAIN','Rain',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_RED_HOT','Red Hot',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_GREEN_HOT','Green Hot',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_SPRING','Spring',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_SUMMER','Summer',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_AUTUMN','Autumn',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_COLORIZATION'),'COLORIZATION_WINTER','Winter',TRUE);

-- changeset longnd:056
INSERT INTO lookup_type (code, name)
VALUES (
        'CONDITION_ROI_TYPE',
        'Condition roi type'
       );

-- changeset longnd:057
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_ROI_TYPE'),'ROI_25','Center 25%',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_ROI_TYPE'),'ROI_50','Center 50%',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_ROI_TYPE'),'ROI_75','Center 75%',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_ROI_TYPE'),'ROI_FULL','Full Screen',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_ROI_TYPE'),'ROI_GROUND','Ground',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_ROI_TYPE'),'ROI_HORIZON','Horizon',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_ROI_TYPE'),'ROI_SKY','Sky',TRUE);

-- changeset longnd:058
INSERT INTO lookup_type (code, name)
VALUES (
           'CONDITION_FLIP',
           'Condition flip'
       );

-- changeset longnd:059
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_FLIP'),'FLIP_0','0°',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_FLIP'),'FLIP_90','90°',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_FLIP'),'FLIP_180','180°',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_FLIP'),'FLIP_270','270°',TRUE);

-- changeset longnd:060
INSERT INTO lookup_type (code, name)
VALUES (
           'CONDITION_GAIN_MODE',
           'Condition gain mode'
       );

-- changeset longnd:061
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_GAIN_MODE'),'GAIN_LOW_TEMPERATURE','Low Temperature',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_GAIN_MODE'),'GAIN_HIGH_TEMPERATURE','High Temperature',TRUE);

-- changeset longnd:062
INSERT INTO lookup_type (code, name)
VALUES (
           'CONDITION_FFC_MODE',
           'Condition ffc mode'
       );

-- changeset longnd:063
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_FFC_MODE'),'FFC_AUTO','Auto',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'CONDITION_FFC_MODE'),'FFC_MANUAL','Manual',TRUE);

-- changeset longnd:064
INSERT INTO lookup_type (code, name)
VALUES (
           'TCPIP_ETHERNET_CARD',
           'Tcp/ip ethernet card'
       );

-- changeset longnd:065
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'TCPIP_ETHERNET_CARD'),'CARD_WIRE','Wire',TRUE);

-- changeset longnd:066
INSERT INTO lookup_type (code, name)
VALUES (
           'VIDEO_ENCODE_MODE',
           'Video encode mode'
       );

-- changeset longnd:067
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_ENCODE_MODE'),'ENCODE_H264','H.264',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_ENCODE_MODE'),'ENCODE_H264B','H.264B',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_ENCODE_MODE'),'ENCODE_H264H','H.264H',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_ENCODE_MODE'),'ENCODE_MJPEG','MJPEG',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_ENCODE_MODE'),'ENCODE_H265','H.265',TRUE);

-- changeset longnd:068
INSERT INTO lookup_type (code, name)
VALUES (
           'VIDEO_RESOLUTION',
           'Video resolution'
       );

-- changeset longnd:069
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_RESOLUTION'),'RESOLUTION_SXGA','1280*1024(SXGA)',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_RESOLUTION'),'RESOLUTION_1.3M','1280*960(1.3M)',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_RESOLUTION'),'RESOLUTION_720P','1280*720(720P)',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_RESOLUTION'),'RESOLUTION_400x300','400*300(400x300)',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_RESOLUTION'),'RESOLUTION_640x512','640*512(640x512)',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_RESOLUTION'),'RESOLUTION_640x480','640*480(VGA)',TRUE);

-- changeset longnd:070
INSERT INTO lookup_type (code, name)
VALUES (
           'VIDEO_FRAME_RATE',
           'Video frame rate'
       );

-- changeset longnd:071
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_1','1',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_2','2',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_3','3',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_4','4',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_5','5',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_6','6',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_7','7',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_8','8',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_9','9',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_10','10',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_11','11',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_12','12',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_13','13',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_14','14',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_15','15',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_16','16',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_17','17',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_18','18',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_19','19',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_20','20',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_21','21',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_22','22',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_23','23',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_24','24',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_FRAME_RATE'),'FPS_25','25',TRUE);

-- changeset longnd:072
INSERT INTO lookup_type (code, name)
VALUES (
           'VIDEO_BIT_RATE_TYPE',
           'Video bit rate type'
       );

-- changeset longnd:073
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE_TYPE'),'BRT_CBR','CBR',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE_TYPE'),'BRT_VBR','VBR',TRUE);

-- changeset longnd:074
INSERT INTO lookup_type (code, name)
VALUES (
           'VIDEO_BIT_RATE',
           'Video bit rate'
       );

-- changeset longnd:075
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_160','160',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_192','192',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_224','224',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_256','256',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_320','320',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_384','384',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_448','448',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_512','512',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_1280','1280',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_1536','1536',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_1792','1792',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_2048','2048',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_4096','4096',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_BIT_RATE'),'BRATE_6144','6144',TRUE);

-- changeset longnd:076
INSERT INTO lookup_type (code, name)
VALUES (
           'VIDEO_SVC',
           'Video svc'
       );

-- changeset longnd:077
INSERT INTO lookup_value (lookup_type_id, code, name, is_active)
VALUES
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_SVC'),'SVC_1','1',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_SVC'),'SVC_2','2',TRUE),
    ((SELECT id FROM lookup_type WHERE code = 'VIDEO_SVC'),'SVC_3','3',TRUE);

-- changeset longnd:078
INSERT INTO lookup_type (code, name, description)
VALUES (
           'DEV_TEST_TYPE',
           'Development test type',
           'Used for developing purposes'
       );

-- changeset longnd:079
INSERT INTO lookup_value (lookup_type_id, code, name, description, is_active)
VALUES ((SELECT id FROM lookup_type WHERE code = 'DEV_TEST_TYPE'), 'DEV_TEST', 'Development test', 'Used for developing purposes', TRUE);