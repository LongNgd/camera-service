package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.cameraservice.domain.DTO.*;
import vn.atdigital.cameraservice.domain.model.CameraCondition;
import vn.atdigital.cameraservice.service.*;

import java.util.List;
import java.util.Optional;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.*;
import static vn.atdigital.cameraservice.common.Constants.API_SUCCESS_MESSAGE.GET_ACTIVE_PROFILE;
import static vn.atdigital.cameraservice.common.Constants.API_SUCCESS_MESSAGE.GET_PROFILE_CONDITION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camera")
public class CameraController extends CommonController {
    private final CameraService cameraService;
    private final VideoService videoService;
    private final ConditionService conditionService;
    private final AudioService audioService;

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

    @PostMapping("/video/update")
    public ResponseEntity<?> updateVideoCamera(@RequestParam Long cameraId, @RequestBody List<VideoStreamDTO> videoStreamDTO) {
        try {
            videoService.updateCameraVideo(cameraId, videoStreamDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }

    }

    @PostMapping("/audio/update")
    public ResponseEntity<?> updateAudioVideo(@RequestParam Long cameraId, @RequestBody AudioDTO audioDTO) {
        try {
            audioService.updateCameraAudio(cameraId, audioDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
    @PostMapping("/condition/add-profile")
    public ResponseEntity<?> addProfile(@RequestParam Long cameraId, @RequestParam String profileCode, @RequestBody ConditionDTO conditionDTO) throws RuntimeException {
        try {
            conditionService.addProfile(cameraId, profileCode, conditionDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }

    @GetMapping("/condition/active-profile")
    public ResponseEntity<?> getActiveProfile(@RequestParam Long cameraId) {
        try {
            List<String> activeProfileList = conditionService.getActiveProfile(cameraId);
            return toSuccessResult(activeProfileList, RETURN_CODE_OK, GET_ACTIVE_PROFILE);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }

    @GetMapping("/condition/get-by-profile")
    public ResponseEntity<?> getConditionByProfile(@RequestParam Long cameraId, @RequestParam String profileCode) {
        try {
            Optional<CameraCondition> cameraConditionOptional = conditionService.getConditionByProfile(cameraId, profileCode);

            if (cameraConditionOptional.isPresent())
                return toSuccessResult(cameraConditionOptional.get(), RETURN_CODE_OK, GET_PROFILE_CONDITION);
            else return toSuccessResultNull(RETURN_CODE_NO_CONTENT);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
