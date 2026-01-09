package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.cameraservice.domain.DTO.PtzSettingsDTO;
import vn.atdigital.cameraservice.service.PtzService;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_BAD_REQUEST;
import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ptz")
public class PtzSettingController extends CommonController {
    private final PtzService ptzService;

    @PostMapping("/update")
    public ResponseEntity<?> cameraPtzSettingUpdate(@RequestParam Long cameraId, @RequestBody PtzSettingsDTO ptzSettingsDTO) {
        try{
            ptzService.updateCameraPtz(cameraId, ptzSettingsDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        }catch (Exception e){
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
