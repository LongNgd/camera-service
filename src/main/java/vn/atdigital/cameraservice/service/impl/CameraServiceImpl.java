package vn.atdigital.cameraservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vn.atdigital.cameraservice.common.BaseResponse;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.*;
import vn.atdigital.cameraservice.domain.model.*;
import vn.atdigital.cameraservice.feignclient.PeerClient;
import vn.atdigital.cameraservice.helper.CameraHelper;
import vn.atdigital.cameraservice.repository.*;
import vn.atdigital.cameraservice.service.CameraService;

import java.time.LocalDateTime;
import java.util.List;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.INIT_CAMERA;
import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.*;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_TYPE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.*;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;

@Service
@RequiredArgsConstructor
public class CameraServiceImpl implements CameraService {
    private final CameraRepository cameraRepository;
    private final CommonUtils commonUtils;
    private final UserCameraRepository userCameraRepository;
    private final CameraTcpIpRepository cameraTcpIpRepository;
    private final CameraHelper cameraHelper;
    private final TcpIpV4Repository tcpIpV4Repository;
    private final TcpIpV6Repository tcpIpV6Repository;
    private final CameraPortRepository cameraPortRepository;
    private final CameraVideoStreamRepository cameraVideoStreamRepository;
    private final CameraPtzSettingsRepository cameraPtzSettingsRepository;
    private final CameraAudioRepository cameraAudioRepository;
    private final CameraAudioStreamRepository cameraAudioStreamRepository;
    private final CameraConditionRepository cameraConditionRepository;
    private final PeerClient peerClient;
    private final CameraPathRepository cameraPathRepository;

    @Override
    @Transactional
    public void initializeCamera(Long ownerId, String ownerType, CameraInitDTO cameraInitDTO) {
        Camera camera = createCameraCore(ownerId, ownerType, cameraInitDTO.getCameraCore());
        Long cameraId = camera.getId();
        Long auditId = commonUtils.saveActionAudit(ClientContextHolder.getUser().getUsername(), INIT_CAMERA, cameraId, CAMERA_TYPE);
        commonUtils.saveActionDetail(auditId, CAMERA_TABLE, cameraId, null, camera);

        addCameraToUser(cameraId, auditId);
        addCameraPath(cameraId, cameraInitDTO.getConnection(), auditId);
        if (cameraInitDTO.getTcpIp() != null) createTcpIp(cameraId, cameraInitDTO.getTcpIp(), auditId);
        if (cameraInitDTO.getPort() != null) createPort(cameraId, cameraInitDTO.getPort(), auditId);
        if (cameraInitDTO.getCondition() != null) createCondition(cameraId, cameraInitDTO.getCondition(), auditId);
        if (cameraInitDTO.getVideoStreamList() != null) createVideoStreamList(cameraId, cameraInitDTO.getVideoStreamList(), auditId);
        if (cameraInitDTO.getAudio() != null) createAudio(cameraId, cameraInitDTO.getAudio(), auditId);
        if (cameraInitDTO.getPtzSettings() != null) createPtzSettings(cameraId, cameraInitDTO.getPtzSettings(), auditId);
    }

    private Camera createCameraCore(Long ownerId, String ownerType, CameraCoreDTO cameraCore) {
        cameraHelper.validateCameraCore(cameraCore);

        Camera camera = new Camera();
        BeanUtils.copyProperties(cameraCore, camera);
        camera.setOwnerId(ownerId);
        camera.setOwnerType(ownerType);
        camera.setCreatedUser(ClientContextHolder.getUser().getUsername());
        camera.setCreatedDatetime(LocalDateTime.now());
        camera.setStatus(ACTIVE);
        camera = cameraRepository.save(camera);

        return camera;
    }

    private void addCameraToUser(Long cameraId, Long auditId) {
        UserCamera userCamera = UserCamera.builder()
                .userId(ClientContextHolder.getUser().getId())
                .cameraId(cameraId)
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        userCameraRepository.save(userCamera);

        commonUtils.saveActionDetail(auditId, USER_CAMERA_TABLE, userCamera.getId(), null, userCamera);
    }

    private void addCameraPath(Long cameraId, ConnectionDTO connection, Long auditId) {
        BaseResponse<List<PathConfigDTO>> response = peerClient.addPath(List.of(connection));

        PathConfigDTO pathConfig = response.getData().getFirst();

        CameraPath cameraPath = CameraPath.builder()
                .cameraId(cameraId)
                .name(pathConfig.getName())
                .path(pathConfig.getPath())
                .source(pathConfig.getSource())
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        cameraPath = cameraPathRepository.save(cameraPath);

        commonUtils.saveActionDetail(auditId, CAMERA_PATH_TABLE, cameraPath.getId(), null, cameraPath);
    }

    private void createTcpIp(Long cameraId, TcpIpDTO tcpIp, Long auditId) {
        cameraHelper.validateTcpIp(tcpIp);

        CameraTcpIp cameraTcpIp = new CameraTcpIp();
        BeanUtils.copyProperties(tcpIp, cameraTcpIp);
        cameraTcpIp.setCameraId(cameraId);
        cameraTcpIp.setCreatedUser(ClientContextHolder.getUser().getUsername());
        cameraTcpIp.setCreatedDatetime(LocalDateTime.now());
        cameraTcpIp.setStatus(ACTIVE);
        cameraTcpIp = cameraTcpIpRepository.save(cameraTcpIp);

        switch(tcpIp.getIpVersionCode()) {
            case IP_VERSION_IPV4_CODE -> createTcpIpV4(cameraTcpIp.getId(), tcpIp.getSubnetMask(), auditId);
            case IP_VERSION_IPV6_CODE -> createTcpIpV6(cameraTcpIp.getId(), tcpIp.getLinkAddress(), tcpIp.getCidrNotation(), auditId);
        }

        commonUtils.saveActionDetail(auditId, CAMERA_TCP_IP_TABLE, cameraTcpIp.getId(), null, cameraTcpIp);
    }

    private void createTcpIpV4(Long cameraTcpId, String subnetMask, Long auditId) {
        TcpIpV4 tcpIpV4 = TcpIpV4.builder()
                .cameraTcpId(cameraTcpId)
                .subnetMask(subnetMask)
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        tcpIpV4 = tcpIpV4Repository.save(tcpIpV4);

        commonUtils.saveActionDetail(auditId, TCP_IP_V4_TABLE, tcpIpV4.getId(), null, tcpIpV4);
    }

    private void createTcpIpV6(Long cameraTcpId, String linkAddress, String cidrNotation, Long auditId) {
        TcpIpV6 tcpIpV6 = TcpIpV6.builder()
                .cameraTcpId(cameraTcpId)
                .linkAddress(linkAddress)
                .cidrNotation(cidrNotation)
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        tcpIpV6 = tcpIpV6Repository.save(tcpIpV6);

        commonUtils.saveActionDetail(auditId, TCP_IP_V6_TABLE, tcpIpV6.getId(), null, tcpIpV6);
    }

    private void createPort(Long cameraId, PortDTO port, Long auditId) {
        cameraHelper.validatePort(port);

        CameraPort cameraPort = CameraPort.builder()
                .cameraId(cameraId)
                .maxConnection(port.getMaxConnection())
                .tcpPort(port.getTcpPort())
                .udpPort(port.getUdpPort())
                .httpPort(port.getHttpPort())
                .rtspPort(port.getRtspPort())
                .httpsPort(port.getHttpsPort())
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        cameraPort = cameraPortRepository.save(cameraPort);

        commonUtils.saveActionDetail(auditId, CAMERA_PORT_TABLE, cameraPort.getId(), null, cameraPort);
    }

    private void createCondition(Long cameraId, ConditionDTO condition, Long auditId) {
        cameraHelper.validateCondition(condition);

        CameraCondition cameraCondition = new CameraCondition();
        BeanUtils.copyProperties(condition, cameraCondition);
        cameraCondition.setCameraId(cameraId);
        cameraCondition.setCreatedUser(ClientContextHolder.getUser().getUsername());
        cameraCondition.setCreatedDatetime(LocalDateTime.now());
        cameraCondition.setStatus(ACTIVE);

        cameraCondition = cameraConditionRepository.save(cameraCondition);

        commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_TABLE, cameraCondition.getId(), null, cameraCondition);
    }

    private void createVideoStreamList(Long cameraId, List<VideoStreamDTO> videoStreamList, Long auditId) {
        cameraHelper.validateVideoStreamList(videoStreamList);

        videoStreamList.forEach(videoStream -> {
            CameraVideoStream cameraVideoStream = new CameraVideoStream();
            BeanUtils.copyProperties(videoStream, cameraVideoStream);
            cameraVideoStream.setCameraId(cameraId);
            cameraVideoStream.setCreatedUser(ClientContextHolder.getUser().getUsername());
            cameraVideoStream.setCreatedDatetime(LocalDateTime.now());
            cameraVideoStream.setStatus(ACTIVE);
            cameraVideoStream = cameraVideoStreamRepository.save(cameraVideoStream);

            commonUtils.saveActionDetail(auditId, CAMERA_VIDEO_STREAM_TABLE, cameraVideoStream.getId(), null, cameraVideoStream);
        });
    }

    private void createAudio(Long cameraId, AudioDTO audio, Long auditId) {
        cameraHelper.validateAudio(audio);

        CameraAudio cameraAudio = new CameraAudio();
        BeanUtils.copyProperties(audio, cameraAudio);
        cameraAudio.setCameraId(cameraId);
        cameraAudio.setCreatedUser(ClientContextHolder.getUser().getUsername());
        cameraAudio.setCreatedDatetime(LocalDateTime.now());
        cameraAudio.setStatus(ACTIVE);
        cameraAudio = cameraAudioRepository.save(cameraAudio);

        commonUtils.saveActionDetail(auditId, CAMERA_AUDIO_TABLE, cameraAudio.getId(), null, cameraAudio);

        Long cameraAudioId = cameraAudio.getId();
        audio.getAudioStreamList().forEach(audioStream -> createAudioStream(cameraAudioId, audioStream, auditId));
    }

    private void createAudioStream(Long cameraAudioId, AudioStreamDTO audioStream, Long auditId) {
        CameraAudioStream cameraAudioStream = new CameraAudioStream();
        BeanUtils.copyProperties(audioStream, cameraAudioStream);
        cameraAudioStream.setCameraAudioId(cameraAudioId);
        cameraAudioStream.setCreatedUser(ClientContextHolder.getUser().getUsername());
        cameraAudioStream.setCreatedDatetime(LocalDateTime.now());
        cameraAudioStream.setStatus(ACTIVE);
        cameraAudioStream = cameraAudioStreamRepository.save(cameraAudioStream);

        commonUtils.saveActionDetail(auditId, CAMERA_AUDIO_STREAM_TABLE, cameraAudioStream.getId(), null, cameraAudioStream);
    }

    private void createPtzSettings(Long cameraId, PtzSettingsDTO ptzSettings, Long auditId) {
        cameraHelper.validatePtz(ptzSettings);

        CameraPtzSettings cameraPtzSettings = new CameraPtzSettings();
        BeanUtils.copyProperties(ptzSettings, cameraPtzSettings);
        cameraPtzSettings.setCameraId(cameraId);
        cameraPtzSettings.setCreatedUser(ClientContextHolder.getUser().getUsername());
        cameraPtzSettings.setCreatedDatetime(LocalDateTime.now());
        cameraPtzSettings.setStatus(ACTIVE);
        cameraPtzSettings = cameraPtzSettingsRepository.save(cameraPtzSettings);

        commonUtils.saveActionDetail(auditId, CAMERA_PTZ_SETTINGS_TABLE, cameraPtzSettings.getId(), null, cameraPtzSettings);
    }
}
