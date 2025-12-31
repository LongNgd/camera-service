package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TcpIpDTO {
    private String hostName;
    private String ethernetCardCode;
    private String modeCode;
    private String macAddress;
    private String ipVersionCode;
    private String ipAddress;
    private String defaultGateway;
    private String preferredDns;
    private String alternateDns;
    private Boolean isEnableSet;

    // IPv4
    private String subnetMask;

    // IPv6
    private String linkAddress;
    private String cidrNotation;
}
