package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.TcpIpDTO;
import vn.atdigital.cameraservice.domain.DTO.TcpIpRequestDTO;


public interface TcpIpConfigService {
    void updateCamera(Long cameraTcpIpId,
                      TcpIpRequestDTO tcpIpRequestDTO);

  TcpIpDTO getAllTcpIp(Long cameraTcpIpId);
}
