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
@Table(name = "camera_port")
public class CameraPort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_id")
    private Long cameraId;

    @Column(name = "max_connection")
    private Integer maxConnection;

    @Column(name = "tcp_port")
    private Integer tcpPort;

    @Column(name = "udp_port")
    private Integer udpPort;

    @Column(name = "http_port")
    private Integer httpPort;

    @Column(name = "rtsp_port")
    private Integer rtspPort;

    @Column(name = "https_port")
    private Integer httpsPort;

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
