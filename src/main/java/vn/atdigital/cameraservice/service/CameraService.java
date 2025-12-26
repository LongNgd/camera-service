package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.CameraInitDTO;

public interface CameraService {
    void initializeCamera(CameraInitDTO cameraInitDTO);
}
