package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtzSettingsDTO {
    private String protocolCode;
    private String address;
    private String baudRateCode;
    private String dataBitCode;
    private String stopBitCode;
    private String parityCode;
}
