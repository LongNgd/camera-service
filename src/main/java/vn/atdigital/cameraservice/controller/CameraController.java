package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.cameraservice.domain.DTO.CameraInitDTO;
import vn.atdigital.cameraservice.service.CameraService;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_BAD_REQUEST;
import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camera")
public class CameraController extends CommonController {
    private final CameraService cameraService;

    @PostMapping("/create")
    public ResponseEntity<?> initializeCamera(@RequestParam Long ownerId, @RequestParam String ownerType, @RequestBody CameraInitDTO cameraInitDTO) {
        try {
            cameraService.initializeCamera(ownerId, ownerType, cameraInitDTO);
            return toSuccessResultNull(RETURN_CODE_CREATED);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
