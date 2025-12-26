package vn.atdigital.cameraservice.domain.message;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseMessage implements Serializable {
    private String status;
    private boolean success;
    private String message;
}
