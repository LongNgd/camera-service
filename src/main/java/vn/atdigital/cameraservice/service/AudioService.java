package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.AudioDTO;

public interface AudioService {
    void updateCameraAudio(Long cameraId, AudioDTO audioDTO);
}
