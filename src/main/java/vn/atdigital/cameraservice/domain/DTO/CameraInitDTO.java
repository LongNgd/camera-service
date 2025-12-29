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
public class CameraInitDTO {
    private ConnectionDTO connection;
    private CameraCoreDTO cameraCore;
    private TcpIpDTO tcpIp;
    private PortDTO port;
    private ConditionDTO condition;
    private List<VideoStreamDTO> videoStreamList;
    private AudioDTO audio;
    private PtzSettingsDTO ptzSettings;
}
