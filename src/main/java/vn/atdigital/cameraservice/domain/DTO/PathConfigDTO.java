package vn.atdigital.cameraservice.domain.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PathConfigDTO extends BaseDTO implements Serializable {
    /* =======================
     * Basic
     * ======================= */
    private String name;
    private String source;
    private String sourceFingerprint;
    private Boolean sourceOnDemand;
    private String sourceOnDemandStartTimeout;
    private String sourceOnDemandCloseAfter;
    private Integer maxReaders;
    private String srtReadPassphrase;
    private String fallback;
    private Boolean useAbsoluteTimestamp;

    /* =======================
     * Recording
     * ======================= */
    private Boolean record;
    private String recordPath;
    private String recordFormat;
    private String recordPartDuration;
    private String recordMaxPartSize;
    private String recordSegmentDuration;
    private String recordDeleteAfter;

    /* =======================
     * Publisher overrides
     * ======================= */
    private Boolean overridePublisher;
    private String srtPublishPassphrase;

    /* =======================
     * RTSP
     * ======================= */
    private String rtspTransport;
    private Boolean rtspAnyPort;
    private String rtspRangeType;
    private String rtspRangeStart;
    private String rtpSDP;
    private String sourceRedirect;

    /* =======================
     * Raspberry Pi Camera
     * ======================= */
    private Integer rpiCameraCamID;
    private Boolean rpiCameraSecondary;
    private Integer rpiCameraWidth;
    private Integer rpiCameraHeight;
    private Boolean rpiCameraHFlip;
    private Boolean rpiCameraVFlip;
    private Integer rpiCameraBrightness;
    private Integer rpiCameraContrast;
    private Integer rpiCameraSaturation;
    private Integer rpiCameraSharpness;
    private String rpiCameraExposure;
    private String rpiCameraAWB;
    private List<Integer> rpiCameraAWBGains;
    private String rpiCameraDenoise;
    private Integer rpiCameraShutter;
    private String rpiCameraMetering;
    private Integer rpiCameraGain;
    private Integer rpiCameraEV;
    private String rpiCameraROI;
    private Boolean rpiCameraHDR;
    private String rpiCameraTuningFile;
    private String rpiCameraMode;
    private Integer rpiCameraFPS;
    private String rpiCameraAfMode;
    private String rpiCameraAfRange;
    private String rpiCameraAfSpeed;
    private Integer rpiCameraLensPosition;
    private String rpiCameraAfWindow;
    private Integer rpiCameraFlickerPeriod;
    private Boolean rpiCameraTextOverlayEnable;
    private String rpiCameraTextOverlay;
    private String rpiCameraCodec;
    private Integer rpiCameraIDRPeriod;
    private Integer rpiCameraBitrate;
    private String rpiCameraHardwareH264Profile;
    private String rpiCameraHardwareH264Level;
    private String rpiCameraSoftwareH264Profile;
    private String rpiCameraSoftwareH264Level;
    private Integer rpiCameraMJPEGQuality;

    /* =======================
     * Hooks / Commands
     * ======================= */
    private String runOnInit;
    private Boolean runOnInitRestart;
    private String runOnDemand;
    private Boolean runOnDemandRestart;
    private String runOnDemandStartTimeout;
    private String runOnDemandCloseAfter;
    private String runOnUnDemand;
    private String runOnReady;
    private Boolean runOnReadyRestart;
    private String runOnNotReady;
    private String runOnRead;
    private Boolean runOnReadRestart;
    private String runOnUnread;
    private String runOnRecordSegmentCreate;
    private String runOnRecordSegmentComplete;
}