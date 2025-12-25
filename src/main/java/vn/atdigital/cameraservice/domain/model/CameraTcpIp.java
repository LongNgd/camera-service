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
@Table(name = "camera_tcp_ip")
public class CameraTcpIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_id")
    private Long cameraId;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "ethernet_card_code")
    private String ethernetCardCode;

    @Column(name = "mode_code")
    private String modeCode;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "ip_version_code")
    private String ipVersionCode;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "default_gateway")
    private String defaultGateway;

    @Column(name = "preferred_dns")
    private String preferredDns;

    @Column(name = "alternate_dns")
    private String alternateDns;

    @Column(name = "is_enable_set")
    private Boolean isEnableSet;

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
