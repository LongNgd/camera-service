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
@Table(name = "camera_condition")
public class CameraCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_id")
    private Long cameraId;

    @Column(name = "profile_code")
    private String profileCode;

    @Column(name = "colorization_code")
    private String colorizationCode;

    private Integer brightness;
    private Integer contrast;
    private Integer sharpness;

    @Column(name = "detail_enhancement")
    private Integer detailEnhancement;

    @Column(name = "histogram_equalization")
    private Integer histogramEqualization;

    private Integer ezoom;

    @Column(name = "roi_type_code")
    private String roiTypeCode;

    @Column(name = "mirror_code")
    private String mirrorCode;

    @Column(name = "flip_code")
    private String flipCode;

    @Column(name = "basic_nr")
    private Integer basicNr;

    @Column(name = "front_module")
    private Integer frontModule;

    @Column(name = "rear_chip")
    private Integer rearChip;

    @Column(name = "gain_mode")
    private Integer gainMode;

    @Column(name = "agc_plateau")
    private Integer agcPlateau;

    @Column(name = "gain_mode_code")
    private String gainModeCode;

    @Column(name = "ffc_mode_code")
    private String ffcModeCode;

    @Column(name = "ffc_period")
    private Integer ffcPeriod;

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
