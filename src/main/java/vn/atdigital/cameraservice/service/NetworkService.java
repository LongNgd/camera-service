package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.TcpIpRequestDTO;

public interface NetworkService {
    void updateIpAddress(TcpIpRequestDTO tcpIpRequest);
}
