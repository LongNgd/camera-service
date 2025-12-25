package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.CameraVideoStreamMain;

@Repository
public interface CameraVideoStreamMainRepository extends JpaRepository<CameraVideoStreamMain, Long> {
}
