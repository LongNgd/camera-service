package vn.atdigital.cameraservice.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.*;
import vn.atdigital.cameraservice.domain.model.Camera;
import vn.atdigital.cameraservice.domain.model.LookupValue;
import vn.atdigital.cameraservice.repository.LookupValueRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.*;
import static vn.atdigital.cameraservice.common.Constants.LOOKUP_VALUE_CODE.STREAM_SUB_CODE;
import static vn.atdigital.cameraservice.common.utils.CommonUtils.*;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Component
@RequiredArgsConstructor
public class CameraHelper {
    private final CommonUtils commonUtils;
    private final LookupValueRepository lookupValueRepository;

    public void validateCameraCore(CameraCoreDTO cameraCore) {
        Assert.notNull(cameraCore, getMessage("0001.camera.null-or-empty"));
        Assert.notNull(cameraCore.getUsername(), getMessage("0004.camera.username.null-or-empty"));
        Assert.notNull(cameraCore.getPassword(), getMessage("0005.camera.password.null-or-empty"));

        commonUtils.checkCodeExistAllowNull(cameraCore.getLanguageCode());
        commonUtils.checkCodeExistAllowNull(cameraCore.getVideoStandardCode());
    }

    public void validateTcpIp(TcpIpDTO tcpIp) {
        commonUtils.checkCodeExistAllowNull(tcpIp.getEthernetCardCode());
        commonUtils.checkCodeExistAllowNull(tcpIp.getModeCode());
        validateMacAddressAllowNull(tcpIp.getMacAddress());
        commonUtils.checkCodeExist(tcpIp.getIpVersionCode());

        switch(tcpIp.getIpVersionCode()) {
            case IP_VERSION_IPV4_CODE -> {
                validateIpV4(tcpIp.getIpAddress());
                validateIpV4AllowNull(tcpIp.getSubnetMask());
                validateIpV4AllowNull(tcpIp.getDefaultGateway());
                validateIpV4AllowNull(tcpIp.getPreferredDns());
                validateIpV4AllowNull(tcpIp.getAlternateDns());
            }
            case IP_VERSION_IPV6_CODE -> {
                validateIpV6AllowNull(tcpIp.getLinkAddress().split("/")[0]);
                validateCidrNotationAllowNull(tcpIp.getLinkAddress().split("/")[1]);
                validateIpV6(tcpIp.getIpAddress());
                validateCidrNotationAllowNull(tcpIp.getCidrNotation());
                validateIpV6AllowNull(tcpIp.getDefaultGateway());
                validateIpV6AllowNull(tcpIp.getPreferredDns());
                validateIpV6AllowNull(tcpIp.getAlternateDns());
            }
        }
    }

    public void validatePort(PortDTO port) {
        validateInRangeAllowNull(port.getMaxConnection(), 1, 20);
        validateInRange(port.getTcpPort(), 1025, 65534);
        validateInRangeAllowNull(port.getUdpPort(), 1025, 65534);
        validateInRangeAllowNull(port.getHttpPort(), 0, null);
        validateInRange(port.getRtspPort(), 0, null);
        validateInRangeAllowNull(port.getHttpsPort(), 0, null);
    }

    public void validateCondition(ConditionDTO condition) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkCodeExistAllowNull(condition.getProfileCode(), lookupValueMap);
        checkCodeExistAllowNull(condition.getColorizationCode(), lookupValueMap);

        validateInRangeAllowNull(condition.getBrightness(), 0, 100);
        validateInRangeAllowNull(condition.getContrast(), 0, 100);
        validateInRangeAllowNull(condition.getSharpness(), 0, 120);
        validateInRangeAllowNull(condition.getDetailEnhancement(), 0, 128);
        validateInRangeAllowNull(condition.getHistogramEqualization(), 0, 32);
        validateInRangeAllowNull(condition.getEZoom(), 0, 19);
        checkCodeExistAllowNull(condition.getRoiTypeCode(), lookupValueMap);
        checkCodeExistAllowNull(condition.getMirrorCode(), lookupValueMap);
        checkCodeExistAllowNull(condition.getFlipCode(), lookupValueMap);

        validateInRangeAllowNull(condition.getBasicNr(), 0, 100);
        validateInRangeAllowNull(condition.getFrontModule(), 0, 100);
        validateInRangeAllowNull(condition.getRearChip(), 0, 100);

        validateInRangeAllowNull(condition.getGainMode(), 0, 255);
        validateInRangeAllowNull(condition.getAgcPlateau(), 0, 255);
        checkCodeExistAllowNull(condition.getGainModeCode(), lookupValueMap);

        checkCodeExistAllowNull(condition.getFfcModeCode(), lookupValueMap);
        validateInRangeAllowNull(condition.getFfcPeriod(), 0, 1200);
    }

    public void validateVideoStreamList(List<VideoStreamDTO> videoStreamList) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkIfVideoStreamTypeDuplicate(videoStreamList, lookupValueMap);

        for (VideoStreamDTO videoStream : videoStreamList) {
            checkCodeExistAllowNull(videoStream.getEncodeModeCode(), lookupValueMap);
            checkCodeExistAllowNull(videoStream.getResolutionCode(), lookupValueMap);
            checkCodeExistAllowNull(videoStream.getFrameRateCode(), lookupValueMap);
            checkCodeExistAllowNull(videoStream.getBitRateTypeCode(), lookupValueMap);
            checkCodeExistAllowNull(videoStream.getReferenceBitRateCode(), lookupValueMap);
            checkCodeExistAllowNull(videoStream.getBitRateCode(), lookupValueMap);
            checkCodeExistAllowNull(videoStream.getSvcCode(), lookupValueMap);
        }
    }

    public void validateAudio(AudioDTO audio) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkCodeExistAllowNull(audio.getAudioInTypeCode(), lookupValueMap);
        checkCodeExistAllowNull(audio.getNoiseFilterCode(), lookupValueMap);
        validateInRangeAllowNull(audio.getMicrophoneVolume(), 0, null);
        validateInRangeAllowNull(audio.getSpeakerVolume(), 0, null);

        checkIfAudioStreamTypeDuplicate(audio.getAudioStreamList(), lookupValueMap);
        audio.getAudioStreamList().forEach(audioStream -> {
            checkCodeExistAllowNull(audioStream.getTypeCode(), lookupValueMap);
            checkCodeExistAllowNull(audioStream.getEncodeModeCode(), lookupValueMap);
            checkCodeExistAllowNull(audioStream.getSamplingFrequencyCode(), lookupValueMap);
        });
    }

    public void validatePtz(PtzSettingsDTO ptzSettings) {
        Map<String, String> lookupValueMap = getLookUpValueMap();

        checkCodeExistAllowNull(ptzSettings.getProtocolCode(), lookupValueMap);
        checkCodeExistAllowNull(ptzSettings.getBaudRateCode(), lookupValueMap);
        checkCodeExistAllowNull(ptzSettings.getDataBitCode(), lookupValueMap);
        checkCodeExistAllowNull(ptzSettings.getStopBitCode(), lookupValueMap);
        checkCodeExistAllowNull(ptzSettings.getParityCode(), lookupValueMap);
    }

    public void validateUpdateCameraCore(Camera camera, CameraCoreDTO cameraCoreDTO) {
        checkValueMatch(camera.getUsername(), cameraCoreDTO.getUsername());
        checkValueMatch(camera.getPassword(), cameraCoreDTO.getPassword());
    }

    private void checkIfVideoStreamTypeDuplicate(List<VideoStreamDTO> videoStreamList, Map<String, String> lookupValueMap) {
        boolean hasMain = false;
        boolean hasSub = false;

        for (VideoStreamDTO videoStream : videoStreamList) {
            checkCodeExistAllowNull(videoStream.getTypeCode(), lookupValueMap);
            if (videoStream.getTypeCode().equals(STREAM_MAIN_CODE) && !hasMain) hasMain = true;
            else if (videoStream.getTypeCode().equals(STREAM_SUB_CODE) && !hasSub) hasSub = true;
            else throw new RuntimeException(getMessage("0001.video-stream.type.exists", lookupValueMap.get(videoStream.getTypeCode())));
        }
    }

    private void checkIfAudioStreamTypeDuplicate(List<AudioStreamDTO> audioStreamList, Map<String, String> lookupValueMap) {
        boolean hasMain = false;
        boolean hasSub = false;

        for (AudioStreamDTO audioStream : audioStreamList) {
            checkCodeExistAllowNull(audioStream.getTypeCode(), lookupValueMap);
            if (audioStream.getTypeCode().equals(STREAM_MAIN_CODE) && !hasMain) hasMain = true;
            else if (audioStream.getTypeCode().equals(STREAM_SUB_CODE) && !hasSub) hasSub = true;
            else throw new RuntimeException(getMessage("0001.audio-stream.type.exists", lookupValueMap.get(audioStream.getTypeCode())));
        }
    }

    private void checkValueMatch(String origin, String update) {
        if (!origin.equals(update)) {
            throw new RuntimeException(getMessage("0002.common.value.not-match-original", origin, update));
        }
    }

    private Map<String, String> getLookUpValueMap() {
        Map<String, String> lookupValueMap = new HashMap<>();

        List<LookupValue> lookUpValueList = lookupValueRepository.findByIsActive(true);
        lookUpValueList.forEach(lookupValue -> lookupValueMap.put(lookupValue.getCode(), lookupValue.getName()));

        return lookupValueMap;
    }
}
