package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionDTO {
    private String profileCode;
    private String colorizationCode;

    // Condition basic
    private Integer brightness;
    private Integer contrast;
    private Integer sharpness;
    private Integer detailEnhancement;
    private Integer histogramEqualization;
    private Integer eZoom;
    private String roiTypeCode;
    private String mirrorCode;
    private String flipCode;

    // Condition image
    private Integer basicNr;
    private Integer frontModule;
    private Integer rearChip;

    // Condition agc
    private Integer gainMode;
    private Integer agcPlateau;
    private String gainModeCode;

    // Condition ffc
    private String ffcModeCode;
    private Integer ffcPeriod;
}
