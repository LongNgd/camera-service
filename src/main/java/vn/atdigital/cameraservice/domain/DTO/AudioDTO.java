package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioDTO {
    private String audioInTypeCode;
    private String noiseFilterCode;
    private Integer microphoneVolume;
    private Integer speakerVolume;
    private List<AudioStreamDTO> audioStreamList;
}
