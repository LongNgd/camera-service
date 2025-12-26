package vn.atdigital.cameraservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "camera_video_stream")
public class CameraVideoStream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_id")
    private Long cameraId;

    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "encode_mode_code")
    private String encodeModeCode;

    @Column(name = "resolution_code")
    private String resolutionCode;

    @Column(name = "frame_rate_code")
    private String frameRateCode;

    @Column(name = "bit_rate_type_code")
    private String bitRateTypeCode;

    @Column(name = "reference_bit_rate_code")
    private String referenceBitRateCode;

    @Column(name = "bit_rate_code")
    private String bitRateCode;

    @Column(name = "i_frame_interval")
    private Integer iFrameInterval;

    @Column(name = "svc_code")
    private String svcCode;

    // Main
    @Column(name = "has_watermark_settings")
    private Boolean hasWatermarkSettings;

    @Column(name = "watermark_character")
    private String watermarkCharacter;

    // Sub
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;

    private Long status;
}
