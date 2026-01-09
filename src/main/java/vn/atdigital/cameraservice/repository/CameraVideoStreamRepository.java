package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraVideoStream;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraVideoStreamRepository extends JpaRepository<CameraVideoStream, Long> {
    Optional<CameraVideoStream> findByCameraIdAndTypeCodeAndStatus(Long cameraId, String typeCode, Long active);
     List<CameraVideoStream> findByCameraIdAndStatus(Long cameraId, Long status);
}
