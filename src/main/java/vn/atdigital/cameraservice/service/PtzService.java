package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.PtzSettingsDTO;

public interface PtzService {
    void updateCameraPtz(Long cameraId, PtzSettingsDTO ptzSettingsDTO);
}
