package vn.atdigital.cameraservice.service.impl;

import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.PtzSettingsDTO;
import vn.atdigital.cameraservice.domain.model.CameraPtzSettings;
import vn.atdigital.cameraservice.helper.CameraHelper;
import vn.atdigital.cameraservice.repository.CameraPtzSettingsRepository;
import vn.atdigital.cameraservice.service.PtzService;

import java.time.LocalDateTime;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.UPDATE_CAMERA_PTZ_SETTING;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_PTZ_SETTING;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.CAMERA_PTZ_SETTINGS_TABLE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Service
@Transactional
@RequiredArgsConstructor
public class PtzServiceImpl implements PtzService {
    private final CameraPtzSettingsRepository ptzRepo;
    private final CommonUtils commonUtils;
    private final CameraHelper cameraHelper;
    @Override
    @Transactional
    public void updateCameraPtz(Long cameraId, PtzSettingsDTO ptzSettingsDTO) {
        cameraHelper.validatePtz(ptzSettingsDTO);

        String user = ClientContextHolder.getUser().getUsername();
        CameraPtzSettings cameraPtz = ptzRepo.findByCameraIdAndStatus(cameraId, ACTIVE)
                .orElseThrow(() -> new RuntimeException(getMessage("0001.PTZ-Setting.not.exists")));

        CameraPtzSettings cameraPtzOldData = new CameraPtzSettings();
        BeanUtils.copyProperties(cameraPtz, cameraPtzOldData);

        cameraPtz.setProtocolCode(ptzSettingsDTO.getProtocolCode());
        cameraPtz.setAddress(ptzSettingsDTO.getAddress());
        cameraPtz.setBaudRateCode(ptzSettingsDTO.getBaudRateCode());
        cameraPtz.setDataBitCode(ptzSettingsDTO.getDataBitCode());
        cameraPtz.setStopBitCode(ptzSettingsDTO.getStopBitCode());
        cameraPtz.setParityCode(ptzSettingsDTO.getParityCode());
        cameraPtz.setUpdatedDatetime(LocalDateTime.now());
        cameraPtz.setUpdatedUser(user);

        ptzRepo.save(cameraPtz);

        Long auditId= commonUtils.saveActionAudit(user,UPDATE_CAMERA_PTZ_SETTING,cameraPtz.getId(),CAMERA_PTZ_SETTING);
        commonUtils.saveActionDetail(auditId,CAMERA_PTZ_SETTINGS_TABLE,cameraPtz.getId(),cameraPtzOldData,cameraPtz);

    }
}
