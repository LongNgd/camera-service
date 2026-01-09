package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.VideoStreamDTO;

import java.util.List;

public interface VideoService {
    void updateCameraVideo(Long cameraId, List<VideoStreamDTO> videoStreamDTO);
}
