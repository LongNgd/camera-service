package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraAudioStream;

@Repository
public interface CameraAudioStreamRepository extends JpaRepository<CameraAudioStream, Long> {
}
