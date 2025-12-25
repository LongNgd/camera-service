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
@Table(name = "camera_audio_stream")
public class CameraAudioStream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_audio_id")
    private Long cameraAudioId;

    @Column(name = "stream_type_code")
    private String streamTypeCode;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "encode_mode_code")
    private String encodeModeCode;

    @Column(name = "sampling_frequency_code")
    private String samplingFrequencyCode;

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
