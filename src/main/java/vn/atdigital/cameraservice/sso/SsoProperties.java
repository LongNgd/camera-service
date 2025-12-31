package vn.atdigital.cameraservice.sso;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "smart.sso")
public class SsoProperties {
    private String serverUrl;
    private String clientId;
    private String clientSecret;
    private String logoutCallbackPath = "/auth/sso/logout-callback";
}
