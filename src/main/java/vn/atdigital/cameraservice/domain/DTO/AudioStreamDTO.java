package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioStreamDTO {
    private String typeCode;
    private Boolean isEnabled;
    private String encodeModeCode;
    private String samplingFrequencyCode;
}
