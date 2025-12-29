package vn.atdigital.cameraservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO implements Serializable {
    private String version;
    private String started;
    private String status;
    private String error;
}
