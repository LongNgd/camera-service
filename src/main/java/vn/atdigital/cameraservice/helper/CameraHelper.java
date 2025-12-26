package vn.atdigital.cameraservice.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.*;
import vn.atdigital.cameraservice.domain.model.LookupValue;
import vn.atdigital.cameraservice.repository.LookupValueRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.*;
import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.VIDEO_STREAM_SUB_CODE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.CommonUtils.*;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Component
@RequiredArgsConstructor
public class CameraHelper {
    private final CommonUtils commonUtils;
    private final LookupValueRepository lookupValueRepository;

    public void validateCameraCore(CameraCoreDTO cameraCore) {
        Assert.notNull(cameraCore, getMessage("0001.camera.null-or-empty"));
        Assert.notNull(cameraCore.getOwnerId(), getMessage("0002.camera.owner-id.null-or-empty"));
        Assert.notNull(cameraCore.getOwnerType(), getMessage("0003.camera.owner-type.null-or-empty"));
        Assert.notNull(cameraCore.getUsername(), getMessage("0004.camera.username.null-or-empty"));
        Assert.notNull(cameraCore.getPassword(), getMessage("0005.camera.password.null-or-empty"));

        commonUtils.checkCodeExist(cameraCore.getLanguageCode());
        commonUtils.checkCodeExist(cameraCore.getVideoStandardCode());
    }

    public void validateTcpIp(TcpIpDTO tcpIp) {
        commonUtils.checkCodeExist(tcpIp.getEthernetCardCode());
        commonUtils.checkCodeExist(tcpIp.getModeCode());
        validateMacAddress(tcpIp.getMacAddress());
        commonUtils.checkCodeExist(tcpIp.getIpVersionCode());

        switch(tcpIp.getIpVersionCode()) {
            case IP_VERSION_IPV4_CODE -> {
                validateIpV4(tcpIp.getIpAddress());
                validateIpV4(tcpIp.getSubnetMask());
                validateIpV4(tcpIp.getDefaultGateway());
                validateIpV4(tcpIp.getPreferredDns());
                validateIpV4(tcpIp.getAlternateDns());
            }
            case IP_VERSION_IPV6_CODE -> {
                validateIpV6(tcpIp.getLinkAddress().split("/")[0]);
                validateCidrNotation(tcpIp.getLinkAddress().split("/")[1]);
                validateIpV6(tcpIp.getIpAddress());
                validateCidrNotation(tcpIp.getCidrNotation());
                validateIpV6(tcpIp.getDefaultGateway());
                validateIpV6(tcpIp.getPreferredDns());
                validateIpV6(tcpIp.getAlternateDns());
            }
        }
    }

    public void validatePort(PortDTO port) {
        validateInRange(port.getMaxConnection(), 1, 20);
        validateInRange(port.getTcpPort(), 1025, 65534);
        validateInRange(port.getUdpPort(), 1025, 65534);
        validateInRange(port.getHttpPort(), 0, null);
        validateInRange(port.getRtspPort(), 0, null);
        validateInRange(port.getHttpsPort(), 0, null);
    }

    public void validateCondition(ConditionDTO condition) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkCodeExists(condition.getProfileCode(), lookupValueMap);
        checkCodeExists(condition.getColorizationCode(), lookupValueMap);

        validateInRange(condition.getBrightness(), 0, 100);
        validateInRange(condition.getContrast(), 0, 100);
        validateInRange(condition.getSharpness(), 0, 120);
        validateInRange(condition.getDetailEnhancement(), 0, 128);
        validateInRange(condition.getHistogramEqualization(), 0, 32);
        validateInRange(condition.getEZoom(), 0, 19);
        checkCodeExists(condition.getRoiTypeCode(), lookupValueMap);
        checkCodeExists(condition.getMirrorCode(), lookupValueMap);
        checkCodeExists(condition.getFlipCode(), lookupValueMap);

        validateInRange(condition.getBasicNr(), 0, 100);
        validateInRange(condition.getFrontModule(), 0, 100);
        validateInRange(condition.getRearChip(), 0, 100);

        validateInRange(condition.getGainMode(), 0, 255);
        validateInRange(condition.getAgcPlateau(), 0, 255);
        checkCodeExists(condition.getGainModeCode(), lookupValueMap);

        checkCodeExists(condition.getFfcModeCode(), lookupValueMap);
        validateInRange(condition.getFfcPeriod(), 0, 1200);
    }

    public void validateVideoStreamList(List<VideoStreamDTO> videoStreamList) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkIfVideoStreamTypeDuplicate(videoStreamList, lookupValueMap);

        for (VideoStreamDTO videoStream : videoStreamList) {
            checkCodeExists(videoStream.getEncodeModeCode(), lookupValueMap);
            checkCodeExists(videoStream.getResolutionCode(), lookupValueMap);
            checkCodeExists(videoStream.getFrameRateCode(), lookupValueMap);
            checkCodeExists(videoStream.getBitRateTypeCode(), lookupValueMap);
            checkCodeExists(videoStream.getReferenceBitRateCode(), lookupValueMap);
            checkCodeExists(videoStream.getBitRateCode(), lookupValueMap);
            checkCodeExists(videoStream.getSvcCode(), lookupValueMap);
        }
    }

    public void validateAudio(AudioDTO audio) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkCodeExists(audio.getAudioInTypeCode(), lookupValueMap);
        checkCodeExists(audio.getNoiseFilterCode(), lookupValueMap);
        validateInRange(audio.getMicrophoneVolume(), 0, null);
        validateInRange(audio.getSpeakerVolume(), 0, null);

        checkIfAudioStreamTypeDuplicate(audio.getAudioStreamList(), lookupValueMap);
        audio.getAudioStreamList().forEach(audioStream -> {
            checkCodeExists(audioStream.getTypeCode(), lookupValueMap);
            checkCodeExists(audioStream.getEncodeModeCode(), lookupValueMap);
            checkCodeExists(audioStream.getSamplingFrequencyCode(), lookupValueMap);
        });
    }

    public void validatePtz(PtzSettingsDTO ptzSettings) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkCodeExists(ptzSettings.getProtocolCode(), lookupValueMap);
        checkCodeExists(ptzSettings.getBaudRateCode(), lookupValueMap);
        checkCodeExists(ptzSettings.getDataBitCode(), lookupValueMap);
        checkCodeExists(ptzSettings.getStopBitCode(), lookupValueMap);
        checkCodeExists(ptzSettings.getParityCode(), lookupValueMap);
    }

    private void checkIfVideoStreamTypeDuplicate(List<VideoStreamDTO> videoStreamList, Map<String, String> lookupValueMap) {
        boolean hasMain = false;
        boolean hasSub = false;

        for (VideoStreamDTO videoStream : videoStreamList) {
            checkCodeExists(videoStream.getTypeCode(), lookupValueMap);
            if (videoStream.getTypeCode().equals(VIDEO_STREAM_MAIN_CODE) && !hasMain) hasMain = true;
            else if (videoStream.getTypeCode().equals(VIDEO_STREAM_SUB_CODE) && !hasSub) hasSub = true;
            else throw new RuntimeException(getMessage("0001.video-stream.type.exists", lookupValueMap.get(videoStream.getTypeCode())));
        }
    }

    private void checkIfAudioStreamTypeDuplicate(List<AudioStreamDTO> audioStreamList, Map<String, String> lookupValueMap) {
        boolean hasMain = false;
        boolean hasSub = false;

        for (AudioStreamDTO audioStream : audioStreamList) {
            checkCodeExists(audioStream.getTypeCode(), lookupValueMap);
            checkCodeExists(audioStream.getTypeCode(), lookupValueMap);
            if (audioStream.getTypeCode().equals(VIDEO_STREAM_MAIN_CODE) && !hasMain) hasMain = true;
            else if (audioStream.getTypeCode().equals(VIDEO_STREAM_SUB_CODE) && !hasSub) hasSub = true;
            else throw new RuntimeException(getMessage("0001.audio-stream.type.exists", lookupValueMap.get(audioStream.getTypeCode())));
        }
    }

    private Map<String, String> getLookUpValueMap() {
        Map<String, String> lookupValueMap = new HashMap<>();

        List<LookupValue> lookUpValueList = lookupValueRepository.findByStatus(ACTIVE);
        lookUpValueList.forEach(lookupValue -> lookupValueMap.put(lookupValue.getCode(), lookupValue.getName()));

        return lookupValueMap;
    }
}
