package vn.atdigital.cameraservice.service.impl;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vn.atdigital.cameraservice.common.utils.CommonUtils;

import vn.atdigital.cameraservice.domain.DTO.TcpIpDTO;
import vn.atdigital.cameraservice.domain.DTO.TcpIpRequestDTO;
import vn.atdigital.cameraservice.domain.model.CameraTcpIp;
import vn.atdigital.cameraservice.helper.CameraHelper;
import vn.atdigital.cameraservice.repository.CameraTcpIpRepository;
import vn.atdigital.cameraservice.repository.TcpIpV4Repository;
import vn.atdigital.cameraservice.repository.TcpIpV6Repository;
import vn.atdigital.cameraservice.service.TcpIpConfigService;

import java.time.LocalDateTime;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.UPDATE_CAMERA_TCP_IP;
import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.*;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_TCP_IP;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.CAMERA_TCP_IP_TABLE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Service
@RequiredArgsConstructor
@Transactional
public class TcpIpConfigServiceImpl implements TcpIpConfigService {

    private final CameraTcpIpRepository cameraTcpIpRepository;
    private final CameraHelper cameraHelper;
    private final CommonUtils commonUtils;
    private final TcpIpV6Repository tcpIpV6Repository;
    private final TcpIpV4Repository tcpIpV4Repository;

    @Override
    @Transactional
    public void updateCamera(Long cameraId,
                             TcpIpRequestDTO tcpIpRequestDTO) {
        CameraTcpIp cameraTcpIp = cameraTcpIpRepository.findByCameraIdAndStatus(cameraId, ACTIVE)
                .orElseThrow(() -> new IllegalStateException(getMessage("0002.tcpip.null-or-empty", cameraId)));

        String user = ClientContextHolder.getUser().getUsername();

        CameraTcpIp cameraTcpIpOldData = new CameraTcpIp();
        BeanUtils.copyProperties(cameraTcpIp, cameraTcpIpOldData);

        cameraTcpIp.setHostName(tcpIpRequestDTO.getHostName());
        cameraTcpIp.setIpAddress(tcpIpRequestDTO.getIpAddress());
        cameraTcpIp.setUpdatedUser(user);
        cameraTcpIp.setUpdatedDatetime(LocalDateTime.now());

        cameraTcpIpRepository.save(cameraTcpIp);

        Long auditId = commonUtils.saveActionAudit(user, UPDATE_CAMERA_TCP_IP, cameraTcpIp.getId(), CAMERA_TCP_IP);
        commonUtils.saveActionDetail(auditId, CAMERA_TCP_IP_TABLE, cameraTcpIp.getId(), cameraTcpIpOldData, cameraTcpIp);
        cameraHelper.updateTcpIp(cameraTcpIp, tcpIpRequestDTO, auditId);

    }

    @Override
    public TcpIpDTO getAllTcpIp(Long cameraId) {
        CameraTcpIp cameraTcpIp = cameraTcpIpRepository.findByCameraIdAndStatus(cameraId, ACTIVE).orElseThrow();

        TcpIpDTO tcpIpDTO = new TcpIpDTO();
        BeanUtils.copyProperties(cameraTcpIp, tcpIpDTO);

        if (IP_VERSION_IPV4_CODE.equals(cameraTcpIp.getIpVersionCode().toUpperCase().trim())) {
            tcpIpV4Repository.findByCameraTcpIdAndStatus(cameraTcpIp.getId(), ACTIVE)
                    .ifPresent(v4 -> {
                        tcpIpDTO.setSubnetMask(v4.getSubnetMask());
                    });
        } else if (IP_VERSION_IPV6_CODE.equals(cameraTcpIp.getIpVersionCode().toUpperCase().trim())) {
            tcpIpV6Repository.findByCameraTcpIdAndStatus(cameraTcpIp.getId(), ACTIVE)
                    .ifPresent(v6 -> {
                        tcpIpDTO.setLinkAddress(v6.getLinkAddress());
                        tcpIpDTO.setCidrNotation(v6.getCidrNotation());
                    });
        }
        return tcpIpDTO;
    }
}
