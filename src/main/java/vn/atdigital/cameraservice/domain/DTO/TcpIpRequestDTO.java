package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TcpIpRequestDTO {
    private Long cameraId;
    private String hostName;
    private String ipAddress;
    private String defaultGateway;
    private Boolean isEnableSet;

    // ipV4
    private String subnetMask;

    // ipV6
    private String cidrNotation;
}
