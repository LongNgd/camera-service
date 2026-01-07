package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.cameraservice.domain.DTO.TcpIpRequestDTO;
import vn.atdigital.cameraservice.service.NetworkService;
import vn.atdigital.cameraservice.service.TcpIpConfigService;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_BAD_REQUEST;
import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/network")
public class NetworkController extends CommonController {
    private final TcpIpConfigService tcpIpConfigService;
    @PostMapping("/tcp-ip/update")
    public ResponseEntity<?> updateTcpIpCamera(@RequestParam Long cameraId, @RequestBody TcpIpRequestDTO tcpIpRequestDTO) {
        try {
            tcpIpConfigService.updateCamera(cameraId, tcpIpRequestDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        }catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
