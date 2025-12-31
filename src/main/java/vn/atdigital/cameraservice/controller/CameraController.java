package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.cameraservice.domain.DTO.CameraConfigDTO;
import vn.atdigital.cameraservice.domain.DTO.ConnectionDTO;
import vn.atdigital.cameraservice.service.CameraService;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camera")
public class CameraController extends CommonController {
    private final CameraService cameraService;

    @PostMapping("/connect")
    public ResponseEntity<?> connectCamera(@RequestParam Long ownerId, @RequestParam String ownerType, @RequestBody ConnectionDTO connectionDTO) {
        try {
            cameraService.connectCamera(ownerId, ownerType, connectionDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }

    @PostMapping("/config")
    public ResponseEntity<?> configCamera(@RequestParam Long cameraId, @RequestBody CameraConfigDTO config) {
        try {
            cameraService.configCamera(cameraId, config);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
