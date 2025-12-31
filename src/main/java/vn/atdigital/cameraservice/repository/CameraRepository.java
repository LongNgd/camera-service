package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.Camera;

import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
    Optional<Camera> findByIdAndStatus(Long id, Long status);
}
