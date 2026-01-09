package vn.atdigital.cameraservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.AudioDTO;
import vn.atdigital.cameraservice.domain.DTO.AudioStreamDTO;
import vn.atdigital.cameraservice.domain.model.CameraAudio;
import vn.atdigital.cameraservice.domain.model.CameraAudioStream;
import vn.atdigital.cameraservice.domain.model.CameraVideoStream;
import vn.atdigital.cameraservice.helper.CameraHelper;
import vn.atdigital.cameraservice.repository.CameraAudioRepository;
import vn.atdigital.cameraservice.repository.CameraAudioStreamRepository;
import vn.atdigital.cameraservice.service.AudioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.UPDATE_CAMERA_AUDIO;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_AUDIO;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.CAMERA_AUDIO_STREAM_TABLE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.CAMERA_AUDIO_TABLE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Service
@RequiredArgsConstructor
@Transactional
public class AudioServiceImpl implements AudioService {
    private final CameraAudioRepository cameraAudioRepository;
    private final CameraAudioStreamRepository cameraAudioStreamRepository;
    private final CameraHelper cameraHelper;
    private final CommonUtils commonUtils;

    @Override
    @Transactional
    public void updateCameraAudio(Long cameraId, AudioDTO audioDTO) {
        String user = ClientContextHolder.getUser().getUsername();
        cameraHelper.validateAudio(audioDTO);
        CameraAudio cameraAudio = cameraAudioRepository.findByCameraIdAndStatus(cameraId, ACTIVE)
                .orElseThrow(()-> new IllegalArgumentException(getMessage("0001.audio.not.exists")));
        CameraAudio cameraAudioOldData= new CameraAudio();
        BeanUtils.copyProperties(cameraAudio, cameraAudioOldData);

       updateCameraCommonData(cameraAudio,audioDTO,user);
        cameraAudioRepository.save(cameraAudio);

        Long auditId = commonUtils.saveActionAudit(user,UPDATE_CAMERA_AUDIO,cameraAudio.getCameraId(),CAMERA_AUDIO);

        commonUtils.saveActionDetail(auditId,CAMERA_AUDIO_TABLE,cameraAudio.getId(),cameraAudioOldData,cameraAudio);

        if(audioDTO.getAudioStreamList()!= null && !audioDTO.getAudioStreamList().isEmpty()) {
          updateCameraStreamData(audioDTO.getAudioStreamList(),cameraAudio.getId(),auditId,user);
        }

    }

    private void updateCameraCommonData(CameraAudio cameraAudio, AudioDTO audioDTO, String user){
        cameraAudio.setAudioInTypeCode(audioDTO.getAudioInTypeCode());
        cameraAudio.setNoiseFilterCode(audioDTO.getNoiseFilterCode());
        cameraAudio.setMicrophoneVolume(audioDTO.getMicrophoneVolume());
        cameraAudio.setSpeakerVolume(audioDTO.getSpeakerVolume());
        cameraAudio.setUpdatedUser(user);
        cameraAudio.setUpdatedDatetime(LocalDateTime.now());
    }

    private void updateCameraStreamData(List<AudioStreamDTO> streamDTOs, Long cameraAudioId, Long auditId, String user){
        for (AudioStreamDTO dto : streamDTOs) {
            cameraAudioStreamRepository.findByCameraAudioIdAndTypeCodeAndStatus(cameraAudioId, dto.getTypeCode(), ACTIVE)
                    .ifPresent(stream -> {

                        CameraAudioStream oldData = new CameraAudioStream();
                        BeanUtils.copyProperties(stream, oldData);

                        stream.setIsEnabled(dto.getIsEnabled());
                        stream.setEncodeModeCode(dto.getEncodeModeCode());
                        stream.setSamplingFrequencyCode(dto.getSamplingFrequencyCode());
                        stream.setUpdatedDatetime(LocalDateTime.now());
                        stream.setUpdatedUser(user);

                        cameraAudioStreamRepository.saveAndFlush(stream);

                        commonUtils.saveActionDetail(auditId, CAMERA_AUDIO_STREAM_TABLE, stream.getId(), oldData, stream);
                    });
        }
    }
}
