package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraAudioStream;

import java.util.Optional;

@Repository
public interface CameraAudioStreamRepository extends JpaRepository<CameraAudioStream, Long> {
    Optional<CameraAudioStream> findByCameraAudioIdAndTypeCodeAndStatus(Long cameraAudioId, String typeCode, Long active);
}
