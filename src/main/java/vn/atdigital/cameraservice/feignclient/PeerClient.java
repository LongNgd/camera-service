package vn.atdigital.cameraservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.atdigital.cameraservice.common.BaseResponse;
import vn.atdigital.cameraservice.domain.DTO.ConnectionDTO;
import vn.atdigital.cameraservice.domain.DTO.PathConfigDTO;

import java.util.List;

@FeignClient(
        name = "${feign-client.peer-service.name}",
        url = "${feign-client.peer-service.url}",
        path = "${feign-client.peer-service.path}"
)
public interface PeerClient {
    @PostMapping("/paths/add")
    BaseResponse<List<PathConfigDTO>> addPath(@RequestBody List<ConnectionDTO> paths);
}
