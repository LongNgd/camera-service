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
@Table(name = "tcp_ip_v6")
public class TcpIpV6 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camera_tcp_id")
    private Long cameraTcpId;

    @Column(name = "link_address")
    private String linkAddress;

    @Column(name = "cidr_notation")
    private String cidrNotation;

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
