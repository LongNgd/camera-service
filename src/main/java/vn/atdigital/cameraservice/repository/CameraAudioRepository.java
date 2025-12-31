package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraAudio;

import java.util.Optional;

@Repository
public interface CameraAudioRepository extends JpaRepository<CameraAudio, Long> {
    Optional<CameraAudio> findByCameraIdAndStatus(Long cameraId, Long active);
}
