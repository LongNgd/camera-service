package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortDTO {
    private Integer maxConnection;
    private Integer tcpPort;
    private Integer udpPort;
    private Integer httpPort;
    private Integer rtspPort;
    private Integer httpsPort;
}
