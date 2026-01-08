package vn.atdigital.cameraservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.atdigital.cameraservice.domain.DTO.PortDTO;
import vn.atdigital.cameraservice.domain.DTO.TcpIpDTO;
import vn.atdigital.cameraservice.domain.DTO.TcpIpRequestDTO;
import vn.atdigital.cameraservice.service.PortConfigService;
import vn.atdigital.cameraservice.service.TcpIpConfigService;

import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_BAD_REQUEST;
import static vn.atdigital.cameraservice.common.Constants.API_RESPONSE.RETURN_CODE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/network")
public class NetworkController extends CommonController {
    private final TcpIpConfigService tcpIpConfigService;
    private final PortConfigService portConfigService;

    @PostMapping("/tcp-ip/update")
    public ResponseEntity<?> updateTcpIpCamera(@RequestParam Long cameraId, @RequestBody TcpIpRequestDTO tcpIpRequestDTO) {
        try {
            tcpIpConfigService.updateCamera(cameraId, tcpIpRequestDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }

    @PostMapping("/port/update")
    public ResponseEntity<?> updatePortCamera(@RequestParam Long cameraId, @RequestBody PortDTO portDTO) {
        try {
            portConfigService.updatePortConfig(cameraId, portDTO);
            return toSuccessResultNull(RETURN_CODE_OK);
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }

    @GetMapping("/tcp-ip/take-all")
    public ResponseEntity<?> getTcpIpConfigService(@RequestParam Long cameraId) {
        try {
            TcpIpDTO tcpIp = tcpIpConfigService.getAllTcpIp(cameraId);
            return toSuccessResult(tcpIp, RETURN_CODE_OK, "tcp ip config");
        } catch (Exception e) {
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
    @GetMapping("/port/take-all")
    public ResponseEntity<?> getPortConfigService(@RequestParam Long portId) {
        try {
            PortDTO dto = portConfigService.getAllPort(portId);
            return toSuccessResult(dto,RETURN_CODE_OK,"");
        }catch (Exception e){
            return toExceptionResult(e.getMessage(), RETURN_CODE_BAD_REQUEST);
        }
    }
}
