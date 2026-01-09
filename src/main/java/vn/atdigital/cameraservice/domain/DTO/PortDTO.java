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
    @Builder.Default
    private Integer maxConnection =10;
    @Builder.Default
    private Integer tcpPort = 37777;
    @Builder.Default
    private Integer udpPort =37778;
    @Builder.Default
    private Integer httpPort=80;
    @Builder.Default
    private Integer rtspPort =554;
    @Builder.Default
    private Integer httpsPort =443;
}
