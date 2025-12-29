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
@Table(name = "camera_condition_ffc")
public class CameraConditionFfc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_condition_id")
    private Long cameraConditionId;

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
