package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraPort;

import java.util.Optional;

@Repository
public interface CameraPortRepository extends JpaRepository<CameraPort, Long> {
    Optional<CameraPort> findByCameraIdAndStatus(Long cameraId, Long active);
}
