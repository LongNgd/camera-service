package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.cameraservice.domain.DTO.ConditionDTO;
import vn.atdigital.cameraservice.domain.model.CameraCondition;
import vn.atdigital.cameraservice.service.ConditionService;

import java.util.List;
import java.util.Optional;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.*;
import static vn.atdigital.cameraservice.common.Constants.API_SUCCESS_MESSAGE.GET_ACTIVE_PROFILE;
import static vn.atdigital.cameraservice.common.Constants.API_SUCCESS_MESSAGE.GET_PROFILE_CONDITION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/condition")
public class ConditionController extends CommonController {
    private final ConditionService conditionService;

    @PostMapping("/add-profile")
    public ResponseEntity<?> addProfile(@RequestParam Long cameraId, @RequestParam String profileCode, @RequestBody ConditionDTO conditionDTO) throws RuntimeException {
        try {
            conditionService.addProfile(cameraId, profileCode, conditionDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }

    @GetMapping("/active-profile")
    public ResponseEntity<?> getActiveProfile(@RequestParam Long cameraId) {
        try {
            List<String> activeProfileList = conditionService.getActiveProfile(cameraId);
            return toSuccessResult(activeProfileList, RETURN_CODE_OK, GET_ACTIVE_PROFILE);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-profile")
    public ResponseEntity<?> getConditionByProfile(@RequestParam Long cameraId, @RequestParam String profileCode) {
        try {
            Optional<CameraCondition> cameraConditionOptional = conditionService.getConditionByProfile(cameraId, profileCode);

            if (cameraConditionOptional.isPresent()) return toSuccessResult(cameraConditionOptional.get(), RETURN_CODE_OK, GET_PROFILE_CONDITION);
            else return toSuccessResultNull(RETURN_CODE_NO_CONTENT);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
