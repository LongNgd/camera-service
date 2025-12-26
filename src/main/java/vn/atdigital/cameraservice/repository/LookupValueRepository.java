package vn.atdigital.cameraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.cameraservice.domain.model.LookupValue;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookupValueRepository extends JpaRepository<LookupValue, Long> {
   boolean existsByCodeAndStatus(String code, Long active);

   Optional<LookupValue> findByCodeAndStatus(String typeCode, Long active);

    List<LookupValue> findByStatus(Long active);
}
