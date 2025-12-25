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
@Table(name = "camera_condition_basic")
public class CameraConditionBasic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_id")
    private Long cameraId;

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

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;

    private String status;
}
