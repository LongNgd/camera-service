package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.PortDTO;

public interface PortConfigService {
    void updatePortConfig(Long cameraId, PortDTO portDTO );
    PortDTO getAllPort(Long cameraId);
}
