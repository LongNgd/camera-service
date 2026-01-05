package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.ConditionDTO;
import vn.atdigital.cameraservice.domain.model.CameraCondition;

import java.util.List;
import java.util.Optional;

public interface ConditionService {
    void addProfile(Long cameraId, String profileCode, ConditionDTO conditionDTO) throws RuntimeException;

    List<String> getActiveProfile(Long cameraId);

    Optional<CameraCondition> getConditionByProfile(Long cameraId, String profileCode);
}

