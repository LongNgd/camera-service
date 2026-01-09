package vn.atdigital.cameraservice.service.impl;

import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.PortDTO;
import vn.atdigital.cameraservice.domain.model.CameraPort;
import vn.atdigital.cameraservice.helper.CameraHelper;
import vn.atdigital.cameraservice.repository.CameraPortRepository;
import vn.atdigital.cameraservice.service.PortConfigService;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.UPDATE_CAMERA_PORT;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_PORT;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.CAMERA_PORT_TABLE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Service
@RequiredArgsConstructor
@Transactional
public class PortConfigServiceImpl implements PortConfigService {
    private final CameraPortRepository cameraPortRepository;
    private final CommonUtils commonUtils;
    private final CameraHelper cameraHelper;

    public void updatePortConfig(Long cameraId, PortDTO portDTO) {
        CameraPort cameraPort = cameraPortRepository.findByCameraIdAndStatus(cameraId, ACTIVE).
                orElseThrow(() -> new IllegalArgumentException(getMessage("0001.port.null-or-empty", cameraId)));
        String user = ClientContextHolder.getUser().getUsername();
        CameraPort cameraPortOldData = new CameraPort();
        BeanUtils.copyProperties(cameraPort, cameraPortOldData);

        cameraHelper.portUpdate(cameraPort, portDTO);
        cameraPort.setUpdatedUser(user);

        cameraPortRepository.save(cameraPort);
        Long auditId = commonUtils.saveActionAudit(user, UPDATE_CAMERA_PORT, cameraId, CAMERA_PORT);
        commonUtils.saveActionDetail(auditId, CAMERA_PORT_TABLE, cameraPort.getId(), cameraPortOldData, cameraPort);
    }

    @Override
    public PortDTO getAllPort(Long portId) {
        CameraPort cameraPort = cameraPortRepository.findByCameraIdAndStatus(portId, ACTIVE).orElseThrow();
        PortDTO portDTO = new PortDTO();
        BeanUtils.copyProperties(cameraPort, portDTO);
        return portDTO;
    }
}
