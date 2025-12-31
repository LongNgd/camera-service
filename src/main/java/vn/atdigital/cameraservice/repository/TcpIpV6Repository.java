package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.TcpIpV6;

import java.util.Optional;

@Repository
public interface TcpIpV6Repository extends JpaRepository<TcpIpV6, Long> {
    Optional<TcpIpV6> findByCameraTcpIdAndStatus(Long cameraTcpId, Long active);
}
