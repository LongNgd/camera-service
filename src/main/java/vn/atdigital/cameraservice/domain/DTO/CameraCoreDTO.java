package vn.atdigital.cameraservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CameraCoreDTO {
    private Long ownerId;
    private String ownerType;
    private String name;
    private String languageCode;
    private String videoStandardCode;
    private String username;
    private String password;
}
