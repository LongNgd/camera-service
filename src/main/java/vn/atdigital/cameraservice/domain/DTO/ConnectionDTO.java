package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDTO implements Serializable {
    private String pathName;
    private String userName;
    private String password;
    private String ipAddress;
    private String port;
    private Long channel;
    private Long subtype;
}