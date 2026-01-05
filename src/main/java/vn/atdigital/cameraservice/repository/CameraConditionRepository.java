package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraCondition;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraConditionRepository extends JpaRepository<CameraCondition, Long> {
    List<CameraCondition> findByCameraIdAndStatus(Long cameraId, Long active);

    boolean existsByCameraIdAndProfileCodeAndStatus(Long cameraId, String profileCode, Long active);

    Optional<CameraCondition> findByCameraIdAndProfileCodeAndStatus(Long cameraId, String profileCode, Long active);
}
