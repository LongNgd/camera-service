package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.LookupValue;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookupValueRepository extends JpaRepository<LookupValue, Long> {
    List<LookupValue> findByIsActive(Boolean isActive);

    boolean existsByCodeAndIsActive(String code, Boolean isActive);
}
