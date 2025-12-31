package vn.atdigital.cameraservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import openjoe.smart.sso.base.entity.Result;
import openjoe.smart.sso.base.entity.Token;
import openjoe.smart.sso.base.entity.TokenUser;
import openjoe.smart.sso.client.token.TokenStorage;
import openjoe.smart.sso.client.token.TokenWrapper;
import openjoe.smart.sso.client.util.ClientContextHolder;
import openjoe.smart.sso.client.util.SSOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vn.atdigital.cameraservice.common.ApiResult;
import vn.atdigital.cameraservice.sso.SsoProperties;

import java.net.URI;

@RestController
@RequestMapping("/auth/sso")
public class SsoAuthController {

    private final SsoProperties ssoProperties;
    private final TokenStorage tokenStorage;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate rest = new RestTemplate();

    public SsoAuthController(SsoProperties ssoProperties, TokenStorage tokenStorage) {
        this.ssoProperties = ssoProperties;
        this.tokenStorage = tokenStorage;
    }

    @GetMapping("/login_url")
    public ApiResult<String> loginUrl(@RequestParam String redirectUri) {
        String url = UriComponentsBuilder.fromUriString(ssoProperties.getServerUrl())
                .path("/sso/login")
                .queryParam("clientId", ssoProperties.getClientId())
                .queryParam("redirectUri", redirectUri)
                .build().toUriString();
        return ApiResult.success(url);
    }

    @GetMapping(value = "/access-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> exchangeCode(@RequestParam String code,
                                               HttpServletRequest request,
                                               jakarta.servlet.http.HttpServletResponse response) {
        try {
            ClientContextHolder.create(request, response);
            Result<Token> result = SSOUtils.getHttpAccessToken(code);
            if (!result.isSuccess()) {
                String body = objectMapper.writeValueAsString(ApiResult.error(result.getMessage()));
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(body);
            }
            String body = objectMapper.writeValueAsString(ApiResult.success(result.getData()));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON)
                    .body("{\"code\":-1,\"message\":\"SSO exchange failed\"}");
        } finally {
            ClientContextHolder.reset();
        }
    }

    @GetMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refreshToken(@RequestParam String refreshToken,
                                               HttpServletRequest request,
                                               jakarta.servlet.http.HttpServletResponse response) {
        try {
            ClientContextHolder.create(request, response);
            Result<Token> result = SSOUtils.getHttpRefreshToken(refreshToken);
            if (!result.isSuccess()) {
                String body = objectMapper.writeValueAsString(ApiResult.error(result.getMessage()));
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(body);
            }
            String body = objectMapper.writeValueAsString(ApiResult.success(result.getData()));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON)
                    .body("{\"code\":-1,\"message\":\"Failed to refresh token\"}");
        } finally {
            ClientContextHolder.reset();
        }
    }

    @GetMapping("/logout_url")
    public ApiResult<String> logoutUrl(@RequestParam String redirectUri) {
        String url = UriComponentsBuilder.fromUriString(ssoProperties.getServerUrl())
                .path("/sso/logout")
                .queryParam("redirectUri", redirectUri)
                .build().toUriString();
        return ApiResult.success(url);
    }

    @PostMapping("/logout-callback")
    public ApiResult<Void> logoutCallback(@RequestHeader(value = "logoutRequest", required = false) String logoutHeader,
                                          @RequestParam(name = "logoutRequest", required = false) String logoutParam) {
        String accessToken = (logoutHeader != null && !logoutHeader.isBlank()) ? logoutHeader : logoutParam;
        if (accessToken != null && !accessToken.isBlank() && tokenStorage != null) {
            try { tokenStorage.remove(accessToken); } catch (Exception ignore) {}
        }
        return ApiResult.success(null);
    }

    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkToken(HttpServletRequest request,
                                             @RequestParam(name = "accessToken", required = false) String accessTokenParam) {
        try {
            String headerName = "smart-sso-token-" + ssoProperties.getClientId();
            String accessToken = accessTokenParam;
            if (accessToken == null || accessToken.isBlank()) {
                String h = request.getHeader(headerName);
                if (h != null && !h.isBlank()) accessToken = h;
            }
            if (accessToken == null || accessToken.isBlank()) {
                String resp = objectMapper.writeValueAsString(ApiResult.success(Boolean.FALSE));
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(resp);
            }
            URI uri = UriComponentsBuilder.fromUriString(ssoProperties.getServerUrl())
                    .path("/sso/permission")
                    .queryParam("accessToken", accessToken)
                    .build(true).toUri();
            String ssoJson = rest.getForObject(uri, String.class);
            JsonNode root = objectMapper.readTree(ssoJson);
            boolean ok = (root != null && root.path("code").asInt() == 1);
            String resp = objectMapper.writeValueAsString(ApiResult.success(ok));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(resp);
        } catch (Exception e) {
            try {
                String resp = objectMapper.writeValueAsString(ApiResult.success(Boolean.FALSE));
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(resp);
            } catch (Exception ignore) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"code\":1,\"data\":false}");
            }
        }
    }

    @GetMapping(value = "/permission", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> permission(HttpServletRequest request,
                                             @RequestParam(name = "accessToken", required = false) String accessTokenParam) {
        try {
            String headerName = "smart-sso-token-" + ssoProperties.getClientId();
            String accessToken = accessTokenParam;
            if (accessToken == null || accessToken.isBlank()) {
                String h = request.getHeader(headerName);
                if (h != null && !h.isBlank()) accessToken = h;
            }
            if (accessToken == null || accessToken.isBlank()) {
                return ResponseEntity.status(400)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"code\":-1,\"message\":\"Missing accessToken\"}");
            }
            URI uri = UriComponentsBuilder.fromUriString(ssoProperties.getServerUrl())
                    .path("/sso/permission")
                    .queryParam("accessToken", accessToken)
                    .build(true).toUri();
            String ssoJson = rest.getForObject(uri, String.class);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ssoJson);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"code\":-1,\"message\":\"Failed to get permission\"}");
        }
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> me(HttpServletRequest request,
                                     @RequestParam(name = "accessToken", required = false) String accessTokenParam) {
        try {
            String headerName = "smart-sso-token-" + ssoProperties.getClientId();
            String accessToken = accessTokenParam;
            if (accessToken == null || accessToken.isBlank()) {
                String h = request.getHeader(headerName);
                if (h != null && !h.isBlank()) accessToken = h;
            }
            if (accessToken == null || accessToken.isBlank()) {
                String body = objectMapper.writeValueAsString(ApiResult.error("Missing accessToken"));
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(body);
            }
            TokenWrapper wrapper = tokenStorage.get(accessToken);
            if (wrapper == null || wrapper.checkRefreshExpired()) {
                String body = objectMapper.writeValueAsString(ApiResult.error("Token not found or expired"));
                return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(body);
            }
            Token token = wrapper.getObject();
            TokenUser user = (token != null) ? token.getTokenUser() : null;
            if (user == null) {
                String body = objectMapper.writeValueAsString(ApiResult.error("No user bound to token"));
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(body);
            }
            String body = objectMapper.writeValueAsString(ApiResult.success(user));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON)
                    .body("{\"code\":-1,\"message\":\"Failed to get current user\"}");
        }
    }
}
