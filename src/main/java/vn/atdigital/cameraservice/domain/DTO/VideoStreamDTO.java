package vn.atdigital.cameraservice.domain.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoStreamDTO {
    private String typeCode;
    private String encodeModeCode;
    private String resolutionCode;
    private String frameRateCode;
    private String bitRateTypeCode;
    private String referenceBitRateCode;
    private String bitRateCode;
    @JsonProperty("iFrameInterval")
    private Integer iFrameInterval;
    private String svcCode;
    // Main
    private Boolean hasWatermarkSettings;
    private String watermarkCharacter;
    // Sub
    private Boolean isEnabled;
}
