package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraTcpIp;

import java.util.Optional;

@Repository
public interface CameraTcpIpRepository extends JpaRepository<CameraTcpIp, Long> {
    Optional<CameraTcpIp> findByCameraIdAndStatus(Long cameraId, Long active);
}
