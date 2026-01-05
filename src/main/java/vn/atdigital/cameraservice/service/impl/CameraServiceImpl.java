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
import java.util.NoSuchElementException;
import java.util.Optional;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.CONFIG_CAMERA;
import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.CONNECT_CAMERA;
import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.*;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.CAMERA_TYPE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.*;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.CommonUtils.validateInRange;
import static vn.atdigital.cameraservice.common.utils.CommonUtils.validateIpV4;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

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
    public void connectCamera(Long ownerId, String ownerType, ConnectionDTO connectionDTO) {
        Camera camera = createCameraCore(ownerId, ownerType, connectionDTO.getUserName(), connectionDTO.getPassword());
        Long cameraId = camera.getId();
        Long auditId = commonUtils.saveActionAudit(ClientContextHolder.getUser().getUsername(), CONNECT_CAMERA, cameraId, CAMERA_TYPE);
        commonUtils.saveActionDetail(auditId, CAMERA_TABLE, cameraId, null, camera);

        addCameraToUser(cameraId, auditId);
        addCameraPath(cameraId, connectionDTO, auditId);
        createTcpIp(cameraId, connectionDTO.getIpAddress(), auditId);
        createPort(cameraId, Integer.valueOf(connectionDTO.getPort()), auditId);
    }

    @Override
    @Transactional
    public void configCamera(Long cameraId, CameraConfigDTO cameraInitDTO) {
        Camera camera = cameraRepository.findByIdAndStatus(cameraId, ACTIVE).orElseThrow(() -> new NoSuchElementException(getMessage("0001.camera.null-or-empty", cameraId)));
        Camera oldCamera = new Camera();
        BeanUtils.copyProperties(camera, oldCamera);
        configCameraCore(camera, cameraInitDTO.getCameraCore());
        Long auditId = commonUtils.saveActionAudit(ClientContextHolder.getUser().getUsername(), CONFIG_CAMERA, cameraId, CAMERA_TYPE);
        commonUtils.saveActionDetail(auditId, CAMERA_TABLE, cameraId, oldCamera, camera);

        configTcpIp(cameraId, cameraInitDTO.getTcpIp(), auditId);
        configPort(cameraId, cameraInitDTO.getPort(), auditId);
        configCondition(cameraId, cameraInitDTO.getCondition(), auditId);
        configVideoStreamList(cameraId, cameraInitDTO.getVideoStreamList(), auditId);
        configAudio(cameraId, cameraInitDTO.getAudio(), auditId);
        configPtzSettings(cameraId, cameraInitDTO.getPtzSettings(), auditId);
    }

    private Camera createCameraCore(Long ownerId, String ownerType, String username, String password) {
        Camera camera = Camera.builder()
                .ownerId(ownerId)
                .ownerType(ownerType)
                .username(username)
                .password(password)
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();

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

    private void createTcpIp(Long cameraId, String ipAddress, Long auditId) {
        validateIpV4(ipAddress);

        CameraTcpIp tcpIp = CameraTcpIp.builder()
                .cameraId(cameraId)
                .ethernetCardCode(TCPIP_CARD_WIRE)
                .modeCode(TCPIP_MODE_STATIC)
                .ipVersionCode(IP_VERSION_IPV4_CODE)
                .ipAddress(ipAddress)
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();

        tcpIp = cameraTcpIpRepository.save(tcpIp);

        commonUtils.saveActionDetail(auditId, CAMERA_TCP_IP_TABLE, tcpIp.getId(), null, tcpIp);
    }

    private void createPort(Long cameraId, Integer rtspPort, Long auditId) {
        validateInRange(rtspPort, 0, null);

        CameraPort port = CameraPort.builder()
                .cameraId(cameraId)
                .rtspPort(rtspPort)
                .createdUser(ClientContextHolder.getUser().getUsername())
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();

        port = cameraPortRepository.save(port);

        commonUtils.saveActionDetail(auditId, CAMERA_PORT_TABLE, port.getId(), null, port);
    }

    private void configTcpIpV4(Long cameraTcpId, String subnetMask, Long auditId) {
        Optional<TcpIpV4> tcpIpV4Optional = tcpIpV4Repository.findByCameraTcpIdAndStatus(cameraTcpId, ACTIVE);

        if (tcpIpV4Optional.isPresent()) {
            TcpIpV4 tcpIpV4 = tcpIpV4Optional.get();
            TcpIpV4 oldTcpIpV4 = new TcpIpV4();
            BeanUtils.copyProperties(tcpIpV4, oldTcpIpV4);

            tcpIpV4.setSubnetMask(subnetMask);
            tcpIpV4.setUpdatedUser(ClientContextHolder.getUser().getUsername());
            tcpIpV4.setUpdatedDatetime(LocalDateTime.now());

            tcpIpV4 = tcpIpV4Repository.save(oldTcpIpV4);

            commonUtils.saveActionDetail(auditId, TCP_IP_V4_TABLE, tcpIpV4.getId(), oldTcpIpV4, tcpIpV4);
        } else {
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
    }

    private void configTcpIpV6(Long cameraTcpId, String linkAddress, String cidrNotation, Long auditId) {
        Optional<TcpIpV6> tcpIpV6Optional = tcpIpV6Repository.findByCameraTcpIdAndStatus(cameraTcpId, ACTIVE);

        if (tcpIpV6Optional.isPresent()) {
            TcpIpV6 tcpIpV6 = tcpIpV6Optional.get();
            TcpIpV6 oldTcpIpV6 = new TcpIpV6();
            BeanUtils.copyProperties(tcpIpV6, oldTcpIpV6);

            tcpIpV6.setLinkAddress(linkAddress);
            tcpIpV6.setCidrNotation(cidrNotation);
            tcpIpV6.setUpdatedUser(ClientContextHolder.getUser().getUsername());
            tcpIpV6.setUpdatedDatetime(LocalDateTime.now());

            tcpIpV6 = tcpIpV6Repository.save(oldTcpIpV6);

            commonUtils.saveActionDetail(auditId, TCP_IP_V6_TABLE, tcpIpV6.getId(), oldTcpIpV6, tcpIpV6);
        } else {
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
    }

    private void configCameraCore(Camera camera, CameraCoreDTO cameraCoreDTO) {
        cameraHelper.validateUpdateCameraCore(camera, cameraCoreDTO);

        BeanUtils.copyProperties(cameraCoreDTO, camera);
        camera.setUpdatedUser(ClientContextHolder.getUser().getUsername());
        camera.setUpdatedDatetime(LocalDateTime.now());

        cameraRepository.save(camera);
    }

    private void configTcpIp(Long cameraId, TcpIpDTO tcpIpDTO, Long auditId) {
        CameraTcpIp cameraTcpIp = cameraTcpIpRepository.findByCameraIdAndStatus(cameraId, ACTIVE).orElseThrow(() -> new NoSuchElementException(getMessage("0002.tcpip.null-or-empty", cameraId)));

        CameraTcpIp oldTcpIp = new CameraTcpIp();
        BeanUtils.copyProperties(cameraTcpIp, oldTcpIp);

        cameraHelper.validateTcpIp(tcpIpDTO);
        BeanUtils.copyProperties(tcpIpDTO, cameraTcpIp);
        cameraTcpIp.setUpdatedUser(ClientContextHolder.getUser().getUsername());
        cameraTcpIp.setUpdatedDatetime(LocalDateTime.now());

        cameraTcpIp = cameraTcpIpRepository.save(cameraTcpIp);

        commonUtils.saveActionDetail(auditId, CAMERA_TCP_IP_TABLE, cameraTcpIp.getId(), oldTcpIp, cameraTcpIp);

        switch(tcpIpDTO.getIpVersionCode()) {
            case IP_VERSION_IPV4_CODE -> configTcpIpV4(cameraTcpIp.getId(), tcpIpDTO.getSubnetMask(), auditId);
            case IP_VERSION_IPV6_CODE -> configTcpIpV6(cameraTcpIp.getId(), tcpIpDTO.getLinkAddress(), tcpIpDTO.getCidrNotation(), auditId);
        }
    }

    private void configPort(Long cameraId, PortDTO portDTO, Long auditId) {
        CameraPort cameraPort = cameraPortRepository.findByCameraIdAndStatus(cameraId, ACTIVE).orElseThrow(() -> new NoSuchElementException(getMessage("0001.port.null-or-empty", cameraId)));

        CameraPort oldCameraPort = new CameraPort();
        BeanUtils.copyProperties(cameraPort, oldCameraPort);

        cameraHelper.validatePort(portDTO);
        BeanUtils.copyProperties(portDTO, cameraPort);
        cameraPort.setUpdatedUser(ClientContextHolder.getUser().getUsername());
        cameraPort.setUpdatedDatetime(LocalDateTime.now());

        cameraPort = cameraPortRepository.save(cameraPort);

        commonUtils.saveActionDetail(auditId, CAMERA_PORT_TABLE, cameraPort.getId(), oldCameraPort, cameraPort);
    }

    private void configCondition(Long cameraId, ConditionDTO conditionDTO, Long auditId) {
        Optional<CameraCondition> cameraConditionOptional = cameraConditionRepository.findByCameraIdAndProfileCodeAndStatus(cameraId, conditionDTO.getProfileCode(), ACTIVE);

        cameraHelper.validateCondition(conditionDTO);

        if (cameraConditionOptional.isPresent()) {
            CameraCondition cameraCondition = cameraConditionOptional.get();
            CameraCondition oldCameraCondition = new CameraCondition();
            BeanUtils.copyProperties(cameraCondition, oldCameraCondition);

            BeanUtils.copyProperties(conditionDTO, cameraCondition);
            cameraCondition.setUpdatedUser(ClientContextHolder.getUser().getUsername());
            cameraCondition.setUpdatedDatetime(LocalDateTime.now());

            cameraCondition = cameraConditionRepository.save(cameraCondition);

            commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_TABLE, cameraCondition.getId(), oldCameraCondition, cameraCondition);
        } else {
            CameraCondition cameraCondition = new CameraCondition();
            BeanUtils.copyProperties(conditionDTO, cameraCondition);
            cameraCondition.setCameraId(cameraId);
            cameraCondition.setCreatedUser(ClientContextHolder.getUser().getUsername());
            cameraCondition.setCreatedDatetime(LocalDateTime.now());
            cameraCondition.setStatus(ACTIVE);

            cameraCondition = cameraConditionRepository.save(cameraCondition);

            commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_TABLE, cameraCondition.getId(), null, cameraCondition);
        }
    }

    private void configVideoStreamList(Long cameraId, List<VideoStreamDTO> videoStreamList, Long auditId) {
        cameraHelper.validateVideoStreamList(videoStreamList);

        videoStreamList.forEach(videoStreamDTO -> {
            Optional<CameraVideoStream> cameraVideoStreamOptional = cameraVideoStreamRepository.findByCameraIdAndTypeCodeAndStatus(cameraId, videoStreamDTO.getTypeCode(), ACTIVE);

            if (cameraVideoStreamOptional.isPresent()) {
                CameraVideoStream cameraVideoStream = cameraVideoStreamOptional.get();
                CameraVideoStream oldCameraVideoStream = new CameraVideoStream();
                BeanUtils.copyProperties(cameraVideoStream, oldCameraVideoStream);

                BeanUtils.copyProperties(videoStreamDTO, cameraVideoStream);
                cameraVideoStream.setUpdatedUser(ClientContextHolder.getUser().getUsername());
                cameraVideoStream.setUpdatedDatetime(LocalDateTime.now());

                cameraVideoStream = cameraVideoStreamRepository.save(cameraVideoStream);

                commonUtils.saveActionDetail(auditId, CAMERA_VIDEO_STREAM_TABLE, cameraVideoStream.getId(), oldCameraVideoStream, cameraVideoStream);
            } else {
                CameraVideoStream cameraVideoStream = new CameraVideoStream();
                BeanUtils.copyProperties(videoStreamDTO, cameraVideoStream);
                cameraVideoStream.setCameraId(cameraId);
                cameraVideoStream.setCreatedUser(ClientContextHolder.getUser().getUsername());
                cameraVideoStream.setCreatedDatetime(LocalDateTime.now());
                cameraVideoStream.setStatus(ACTIVE);
                cameraVideoStream = cameraVideoStreamRepository.save(cameraVideoStream);

                commonUtils.saveActionDetail(auditId, CAMERA_VIDEO_STREAM_TABLE, cameraVideoStream.getId(), null, cameraVideoStream);
            }
        });
    }

    private void configAudio(Long cameraId, AudioDTO audioDTO, Long auditId) {
        Optional<CameraAudio> cameraAudioOptional = cameraAudioRepository.findByCameraIdAndStatus(cameraId, ACTIVE);

        cameraHelper.validateAudio(audioDTO);

        Long cameraAudioId;
        if (cameraAudioOptional.isPresent()) {
            CameraAudio cameraAudio = cameraAudioOptional.get();
            CameraAudio oldCameraAudio = new CameraAudio();
            BeanUtils.copyProperties(cameraAudio, oldCameraAudio);

            BeanUtils.copyProperties(audioDTO, cameraAudio);
            cameraAudio.setUpdatedUser(ClientContextHolder.getUser().getUsername());
            cameraAudio.setUpdatedDatetime(LocalDateTime.now());

            cameraAudio = cameraAudioRepository.save(cameraAudio);
            cameraAudioId = cameraAudio.getId();

            commonUtils.saveActionDetail(auditId, CAMERA_AUDIO_TABLE, cameraAudio.getId(), oldCameraAudio, cameraAudio);
        } else {
            CameraAudio cameraAudio = new CameraAudio();
            BeanUtils.copyProperties(audioDTO, cameraAudio);
            cameraAudio.setCameraId(cameraId);
            cameraAudio.setCreatedUser(ClientContextHolder.getUser().getUsername());
            cameraAudio.setCreatedDatetime(LocalDateTime.now());
            cameraAudio.setStatus(ACTIVE);

            cameraAudio = cameraAudioRepository.save(cameraAudio);
            cameraAudioId = cameraAudio.getId();

            commonUtils.saveActionDetail(auditId, CAMERA_AUDIO_TABLE, cameraAudio.getId(), null, cameraAudio);
        }

        audioDTO.getAudioStreamList().forEach(audioStream -> configAudioStream(cameraAudioId, audioStream, auditId));
    }

    private void configAudioStream(Long cameraAudioId, AudioStreamDTO audioStreamDTO, Long auditId) {
        Optional<CameraAudioStream> cameraAudioStreamOptional = cameraAudioStreamRepository.findByCameraAudioIdAndTypeCodeAndStatus(cameraAudioId, audioStreamDTO.getTypeCode(), ACTIVE);

        if (cameraAudioStreamOptional.isPresent()) {
            CameraAudioStream cameraAudioStream = cameraAudioStreamOptional.get();
            CameraAudioStream oldCameraAudioStream = new CameraAudioStream();
            BeanUtils.copyProperties(cameraAudioStream, oldCameraAudioStream);

            BeanUtils.copyProperties(audioStreamDTO, cameraAudioStream);
            cameraAudioStream.setUpdatedUser(ClientContextHolder.getUser().getUsername());
            cameraAudioStream.setUpdatedDatetime(LocalDateTime.now());

            cameraAudioStream = cameraAudioStreamRepository.save(cameraAudioStream);

            commonUtils.saveActionDetail(auditId, CAMERA_AUDIO_STREAM_TABLE, cameraAudioStream.getId(), oldCameraAudioStream, cameraAudioStream);
        } else {
            CameraAudioStream cameraAudioStream = new CameraAudioStream();
            BeanUtils.copyProperties(audioStreamDTO, cameraAudioStream);
            cameraAudioStream.setCameraAudioId(cameraAudioId);
            cameraAudioStream.setCreatedUser(ClientContextHolder.getUser().getUsername());
            cameraAudioStream.setCreatedDatetime(LocalDateTime.now());
            cameraAudioStream.setStatus(ACTIVE);

            cameraAudioStream = cameraAudioStreamRepository.save(cameraAudioStream);

            commonUtils.saveActionDetail(auditId, CAMERA_AUDIO_STREAM_TABLE, cameraAudioStream.getId(), null, cameraAudioStream);
        }
    }

    private void configPtzSettings(Long cameraId, PtzSettingsDTO ptzSettingsDTO, Long auditId) {
        Optional<CameraPtzSettings> cameraPtzSettingsOptional = cameraPtzSettingsRepository.findByCameraIdAndStatus(cameraId, ACTIVE);

        cameraHelper.validatePtz(ptzSettingsDTO);

        if (cameraPtzSettingsOptional.isPresent()) {
            CameraPtzSettings cameraPtzSettings = cameraPtzSettingsOptional.get();
            CameraPtzSettings oldCameraPtzSettings = new CameraPtzSettings();
            BeanUtils.copyProperties(cameraPtzSettings, oldCameraPtzSettings);

            BeanUtils.copyProperties(ptzSettingsDTO, cameraPtzSettings);
            cameraPtzSettings.setUpdatedUser(ClientContextHolder.getUser().getUsername());
            cameraPtzSettings.setUpdatedDatetime(LocalDateTime.now());

            cameraPtzSettings = cameraPtzSettingsRepository.save(cameraPtzSettings);

            commonUtils.saveActionDetail(auditId, CAMERA_PTZ_SETTINGS_TABLE, cameraPtzSettings.getId(), oldCameraPtzSettings, cameraPtzSettings);
        } else {
            CameraPtzSettings cameraPtzSettings = new CameraPtzSettings();
            BeanUtils.copyProperties(ptzSettingsDTO, cameraPtzSettings);
            cameraPtzSettings.setCameraId(cameraId);
            cameraPtzSettings.setCreatedUser(ClientContextHolder.getUser().getUsername());
            cameraPtzSettings.setCreatedDatetime(LocalDateTime.now());
            cameraPtzSettings.setStatus(ACTIVE);

            cameraPtzSettings = cameraPtzSettingsRepository.save(cameraPtzSettings);

            commonUtils.saveActionDetail(auditId, CAMERA_PTZ_SETTINGS_TABLE, cameraPtzSettings.getId(), null, cameraPtzSettings);
        }
    }
}
