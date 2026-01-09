package vn.atdigital.cameraservice.service.impl;

import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.VideoStreamDTO;
import vn.atdigital.cameraservice.domain.model.CameraVideoStream;
import vn.atdigital.cameraservice.helper.CameraHelper;
import vn.atdigital.cameraservice.repository.CameraVideoStreamRepository;
import vn.atdigital.cameraservice.service.VideoService;

import java.time.LocalDateTime;
import java.util.List;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.UPDATE_CAMERA_VIDEO;
import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.*;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_VIDEO;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.CAMERA_VIDEO_STREAM;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoServiceImpl implements VideoService {
    private final CameraVideoStreamRepository cameraVideoStreamRepository;
    private final CommonUtils commonUtils;
    private final CameraHelper cameraHelper;

    @Override
    @Transactional
    public void updateCameraVideo(Long cameraId, List<VideoStreamDTO> videoStreamDTOs) {
        String username = ClientContextHolder.getUser().getUsername();
        List<CameraVideoStream> existingStreams = cameraVideoStreamRepository.findByCameraIdAndStatus(cameraId, ACTIVE);
        Assert.notEmpty(existingStreams, "0002.video-stream.not.found");
        cameraHelper.validateVideoStreamList(videoStreamDTOs);
        Long auditId = commonUtils.saveActionAudit(username, UPDATE_CAMERA_VIDEO, cameraId, CAMERA_VIDEO);

        for (VideoStreamDTO dto : videoStreamDTOs) {

            CameraVideoStream entity = existingStreams.stream()
                    .filter(s -> dto.getTypeCode().equalsIgnoreCase(s.getTypeCode()))
                    .findAny().orElseThrow(() -> new IllegalStateException(getMessage("0002.video-stream.not.found")));

            CameraVideoStream oldDataSnapshot = new CameraVideoStream();
            BeanUtils.copyProperties(entity, oldDataSnapshot);

            saveVideoDataByTypeCode(entity, dto);

            CameraVideoStream savedEntity = cameraVideoStreamRepository.save(entity);

            commonUtils.saveActionDetail(auditId, CAMERA_VIDEO_STREAM, savedEntity.getId(), oldDataSnapshot, savedEntity
            );
        }
    }

    private void saveVideoDataByTypeCode(CameraVideoStream entity, VideoStreamDTO dto) {
        String type = dto.getTypeCode().toUpperCase();
        if (STREAM_MAIN_CODE.equalsIgnoreCase(type)) {
            if (dto.getIsEnabled() != null) {
                throw new IllegalArgumentException(getMessage("0003.video-stream.type.code.main.is-enable"));
            }
            entity.setHasWatermarkSettings(dto.getHasWatermarkSettings());
            entity.setWatermarkCharacter(dto.getWatermarkCharacter());

        } else if (STREAM_SUB_CODE.equalsIgnoreCase(type)) {
            entity.setIsEnabled(dto.getIsEnabled());
            if (dto.getHasWatermarkSettings() != null || dto.getWatermarkCharacter() != null) {
                throw new IllegalArgumentException(getMessage("0004.video-stream.type.code.sub.hasWatermarkSettings.watermark-character"));
            }
        }
        entity.setTypeCode(dto.getTypeCode());
        entity.setEncodeModeCode(dto.getEncodeModeCode());
        entity.setResolutionCode(dto.getResolutionCode());
        entity.setFrameRateCode(dto.getFrameRateCode());
        entity.setReferenceBitRate(dto.getReferenceBitRateCode());
        entity.setBitRateTypeCode(dto.getBitRateTypeCode());
        entity.setIFrameInterval(dto.getIFrameInterval());
        entity.setSvcCode(dto.getSvcCode());
        entity.setUpdatedUser(ClientContextHolder.getUser().getUsername());
        entity.setUpdatedDatetime(LocalDateTime.now());
        entity.setStatus(ACTIVE);

    }
}
