package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.TcpIpV4;

@Repository
public interface TcpIpV4Repository extends JpaRepository<TcpIpV4, Long> {
}
