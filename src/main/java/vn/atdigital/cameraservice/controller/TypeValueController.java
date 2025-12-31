package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.atdigital.cameraservice.domain.DTO.TypeValueMapDTO;
import vn.atdigital.cameraservice.service.TypeValueService;

import java.util.List;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_BAD_REQUEST;
import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_OK;
import static vn.atdigital.cameraservice.common.Constants.API_SUCCESS_MESSAGE.GET_LOOKUP_INFO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lookup")
public class TypeValueController extends CommonController {
    private final TypeValueService typeValueService;

    @GetMapping("/code-map")
    public ResponseEntity<?> getTypeCodeMap() {
        try {
            List<TypeValueMapDTO> typeValueMapDTOList = typeValueService.getTypeCodeMap();
            return toSuccessResult(typeValueMapDTOList, RETURN_CODE_OK, GET_LOOKUP_INFO);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
