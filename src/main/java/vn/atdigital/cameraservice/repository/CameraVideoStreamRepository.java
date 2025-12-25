package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraVideoStream;

@Repository
public interface CameraVideoStreamRepository extends JpaRepository<CameraVideoStream, Long> {
}
