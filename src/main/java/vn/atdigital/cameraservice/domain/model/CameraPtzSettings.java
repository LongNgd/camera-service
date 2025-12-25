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
@Table(name = "camera_ptz_settings")
public class CameraPtzSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_id")
    private Long cameraId;

    @Column(name = "protocol_code")
    private String protocolCode;

    @Column(name = "address")
    private String address;

    @Column(name = "baud_rate_code")
    private String baudRateCode;

    @Column(name = "data_bit_code")
    private String dataBitCode;

    @Column(name = "stop_bit_code")
    private String stopBitCode;

    @Column(name = "parity_code")
    private String parityCode;

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
