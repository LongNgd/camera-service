package vn.atdigital.cameraservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.*;
import vn.atdigital.cameraservice.domain.model.*;
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
    private final CameraConditionBasicRepository cameraConditionBasicRepository;
    private final CameraConditionImageRepository cameraConditionImageRepository;
    private final CameraConditionAgcRepository cameraConditionAgcRepository;
    private final CameraConditionFfcRepository cameraConditionFfcRepository;

    @Override
    @Transactional
    public void initializeCamera(CameraInitDTO cameraInitDTO) {
        Camera camera = createCameraCore(cameraInitDTO.getCameraCore());
        Long cameraId = camera.getId();
        Long auditId = commonUtils.saveActionAudit("Demo", INIT_CAMERA, cameraId, CAMERA_TYPE);
        commonUtils.saveActionDetail(auditId, CAMERA_TABLE, cameraId, null, camera);

        addCameraToUser(cameraId, auditId);
        createTcpIp(cameraId, cameraInitDTO.getTcpIp(), auditId);
        createPort(cameraId, cameraInitDTO.getPort(), auditId);
        createCondition(cameraId, cameraInitDTO.getCondition(), auditId);
        createVideoStreamList(cameraId, cameraInitDTO.getVideoStreamList(), auditId);
        createAudio(cameraId, cameraInitDTO.getAudio(), auditId);
        createPtzSettings(cameraId, cameraInitDTO.getPtzSettings(), auditId);
    }

    private Camera createCameraCore(CameraCoreDTO cameraCore) {
        cameraHelper.validateCameraCore(cameraCore);

        Camera camera = new Camera();
        BeanUtils.copyProperties(cameraCore, camera);
        camera.setCreatedUser("Demo"); // TODO user real user
        camera.setCreatedDatetime(LocalDateTime.now());
        camera.setStatus(ACTIVE);
        camera = cameraRepository.save(camera);

        return camera;
    }

    private void addCameraToUser(Long cameraId, Long auditId) {
        UserCamera userCamera = UserCamera.builder()
                .userId(-1L) // TODO add real user
                .cameraId(cameraId)
                .createdUser("Demo") // TODO use real user
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        userCameraRepository.save(userCamera);

        commonUtils.saveActionDetail(auditId, USER_CAMERA_TABLE, userCamera.getId(), null, userCamera);
    }

    private void createTcpIp(Long cameraId, TcpIpDTO tcpIp, Long auditId) {
        cameraHelper.validateTcpIp(tcpIp);

        CameraTcpIp cameraTcpIp = new CameraTcpIp();
        BeanUtils.copyProperties(tcpIp, cameraTcpIp);
        cameraTcpIp.setCameraId(cameraId);
        cameraTcpIp.setCreatedUser("Demo"); // TODO user real user
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
                .createdUser("Demo") // TODO user real user
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
                .createdUser("Demo") // TODO user real user
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
                .createdUser("Demo") // TODO user real user
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        cameraPort = cameraPortRepository.save(cameraPort);

        commonUtils.saveActionDetail(auditId, CAMERA_PORT_TABLE, cameraPort.getId(), null, cameraPort);
    }

    private void createCondition(Long cameraId, ConditionDTO condition, Long auditId) {
        cameraHelper.validateCondition(condition);

        CameraCondition cameraCondition = CameraCondition.builder()
                .cameraId(cameraId)
                .profileCode(condition.getProfileCode())
                .colorizationCode(condition.getColorizationCode())
                .createdUser("Demo") // TODO user real user
                .createdDatetime(LocalDateTime.now())
                .status(ACTIVE)
                .build();
        cameraCondition = cameraConditionRepository.save(cameraCondition);

        commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_TABLE, cameraCondition.getId(), null, cameraCondition);

        createConditionBasic(cameraId, condition, auditId);
        createConditionImage(cameraId, condition, auditId);
        createConditionAgc(cameraId, condition, auditId);
        createConditionFfc(cameraId, condition, auditId);
    }

    private void createConditionBasic(Long cameraId, ConditionDTO condition, Long auditId) {
        CameraConditionBasic cameraConditionBasic = new CameraConditionBasic();
        BeanUtils.copyProperties(condition, cameraConditionBasic);
        cameraConditionBasic.setCameraId(cameraId);
        cameraConditionBasic.setCreatedUser("Demo"); // TODO user real user
        cameraConditionBasic.setCreatedDatetime(LocalDateTime.now());
        cameraConditionBasic.setStatus(ACTIVE);
        cameraConditionBasic = cameraConditionBasicRepository.save(cameraConditionBasic);

        commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_BASIC_TABLE, cameraConditionBasic.getId(), null, cameraConditionBasic);
    }

    private void createConditionImage(Long cameraId, ConditionDTO condition, Long auditId) {
        CameraConditionImage cameraConditionImage = new CameraConditionImage();
        BeanUtils.copyProperties(condition, cameraConditionImage);
        cameraConditionImage.setCameraId(cameraId);
        cameraConditionImage.setCreatedUser("Demo"); // TODO user real user
        cameraConditionImage.setCreatedDatetime(LocalDateTime.now());
        cameraConditionImage.setStatus(ACTIVE);
        cameraConditionImage = cameraConditionImageRepository.save(cameraConditionImage);

        commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_IMAGE_TABLE, cameraConditionImage.getId(), null, cameraConditionImage);
    }

    private void createConditionAgc(Long cameraId, ConditionDTO condition, Long auditId) {
        CameraConditionAgc cameraConditionAgc = new CameraConditionAgc();
        BeanUtils.copyProperties(condition, cameraConditionAgc);
        cameraConditionAgc.setCameraId(cameraId);
        cameraConditionAgc.setCreatedUser("Demo"); // TODO user real user
        cameraConditionAgc.setCreatedDatetime(LocalDateTime.now());
        cameraConditionAgc.setStatus(ACTIVE);
        cameraConditionAgc = cameraConditionAgcRepository.save(cameraConditionAgc);

        commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_AGC_TABLE, cameraConditionAgc.getId(), null, cameraConditionAgc);
    }

    private void createConditionFfc(Long cameraId, ConditionDTO condition, Long auditId) {
        CameraConditionFfc cameraConditionFfc = new CameraConditionFfc();
        BeanUtils.copyProperties(condition, cameraConditionFfc);
        cameraConditionFfc.setCameraId(cameraId);
        cameraConditionFfc.setCreatedUser("Demo"); // TODO user real user
        cameraConditionFfc.setCreatedDatetime(LocalDateTime.now());
        cameraConditionFfc.setStatus(ACTIVE);
        cameraConditionFfc = cameraConditionFfcRepository.save(cameraConditionFfc);

        commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_FFC_TABLE, cameraConditionFfc.getId(), null, cameraConditionFfc);
    }

    private void createVideoStreamList(Long cameraId, List<VideoStreamDTO> videoStreamList, Long auditId) {
        cameraHelper.validateVideoStreamList(videoStreamList);

        videoStreamList.forEach(videoStream -> {
            CameraVideoStream cameraVideoStream = new CameraVideoStream();
            BeanUtils.copyProperties(videoStream, cameraVideoStream);
            cameraVideoStream.setCameraId(cameraId);
            cameraVideoStream.setCreatedUser("Demo"); // TODO user real user
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
        cameraAudio.setCreatedUser("Demo"); // TODO user real user
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
        cameraAudioStream.setCreatedUser("Demo"); // TODO user real user
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
        cameraPtzSettings.setCreatedUser("Demo"); // TODO user real user
        cameraPtzSettings.setCreatedDatetime(LocalDateTime.now());
        cameraPtzSettings.setStatus(ACTIVE);
        cameraPtzSettings = cameraPtzSettingsRepository.save(cameraPtzSettings);

        commonUtils.saveActionDetail(auditId, CAMERA_PTZ_SETTINGS_TABLE, cameraPtzSettings.getId(), null, cameraPtzSettings);
    }
}
