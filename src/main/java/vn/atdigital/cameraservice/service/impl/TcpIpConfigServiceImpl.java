package vn.atdigital.cameraservice.service.impl;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vn.atdigital.cameraservice.common.utils.CommonUtils;

import vn.atdigital.cameraservice.domain.DTO.TcpIpRequestDTO;
import vn.atdigital.cameraservice.domain.model.CameraTcpIp;
import vn.atdigital.cameraservice.helper.CameraHelper;
import vn.atdigital.cameraservice.repository.CameraTcpIpRepository;
import vn.atdigital.cameraservice.service.TcpIpConfigService;

import java.time.LocalDateTime;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.UPDATE_CAMERA_TCP_IP;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_TCP_IP;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional
public class TcpIpConfigServiceImpl implements TcpIpConfigService {

    private final CameraTcpIpRepository cameraTcpIpRepository;
    private final CameraHelper cameraHelper;
    private final CommonUtils commonUtils;

    @Override
    @Transactional
    public void updateCamera(Long cameraTcpIpId,
                             TcpIpRequestDTO tcpIpRequestDTO) {
        CameraTcpIp cameraTcpIp = cameraTcpIpRepository.findByCameraIdAndStatus(cameraTcpIpId, ACTIVE)
                .orElseThrow();//TODO add message

        String user = ClientContextHolder.getUser().getUsername();

        CameraTcpIp cameraTcpIpOldData = new CameraTcpIp();
        BeanUtils.copyProperties(cameraTcpIp, cameraTcpIpOldData);

        cameraTcpIp.setHostName(tcpIpRequestDTO.getHostName());
        cameraTcpIp.setIpAddress(tcpIpRequestDTO.getIpAddress());
        cameraTcpIp.setUpdatedUser(user);
        cameraTcpIp.setUpdatedDatetime(LocalDateTime.now());

        cameraTcpIpRepository.save(cameraTcpIp);

        Long auditId = commonUtils.saveActionAudit(user, UPDATE_CAMERA_TCP_IP, cameraTcpIp.getId(), CAMERA_TCP_IP);
        cameraHelper.updateTcpIp(cameraTcpIp, tcpIpRequestDTO, auditId);

    }
}
