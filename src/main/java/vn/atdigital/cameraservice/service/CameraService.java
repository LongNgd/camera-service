package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.CameraConfigDTO;
import vn.atdigital.cameraservice.domain.DTO.ConnectionDTO;

public interface CameraService {
    void connectCamera(Long ownerId, String ownerType, ConnectionDTO connectionDTO);

    void configCamera(Long cameraId, CameraConfigDTO cameraInitDTO);
}
