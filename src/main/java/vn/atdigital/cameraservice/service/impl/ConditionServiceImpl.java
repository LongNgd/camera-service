package vn.atdigital.cameraservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import openjoe.smart.sso.client.util.ClientContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.atdigital.cameraservice.common.utils.CommonUtils;
import vn.atdigital.cameraservice.domain.DTO.ConditionDTO;
import vn.atdigital.cameraservice.domain.model.CameraCondition;
import vn.atdigital.cameraservice.repository.CameraConditionRepository;
import vn.atdigital.cameraservice.service.ConditionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static vn.atdigital.cameraservice.common.Constants.ACTION_CODE.ADD_CONDITION_PROFILE;
import static vn.atdigital.cameraservice.common.Constants.PK_TYPE.*;
import static vn.atdigital.cameraservice.common.Constants.TABLE_NAME.CAMERA_CONDITION_TABLE;
import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {
    private final CameraConditionRepository cameraConditionRepository;
    private final CommonUtils commonUtils;

    @Override
    @Transactional
    public void addProfile(Long cameraId, String profileCode, ConditionDTO conditionDTO) throws RuntimeException {
        if (cameraConditionRepository.existsByCameraIdAndProfileCodeAndStatus(cameraId, profileCode, ACTIVE)) throw new RuntimeException(getMessage("0001.condition.profile.exists", profileCode));
        Assert.isTrue(profileCode.equals(conditionDTO.getProfileCode()), getMessage("0001.condition.profile.not-match"));

        CameraCondition cameraCondition = new CameraCondition();
        BeanUtils.copyProperties(conditionDTO, cameraCondition);
        cameraCondition.setCreatedUser(ClientContextHolder.getUser().getUsername());
        cameraCondition.setCreatedDatetime(LocalDateTime.now());
        cameraCondition.setStatus(ACTIVE);

        cameraCondition = cameraConditionRepository.save(cameraCondition);

        Long auditId = commonUtils.saveActionAudit(ClientContextHolder.getUser().getUsername(), ADD_CONDITION_PROFILE, cameraCondition.getId(), CAMERA_CONDITION_TYPE);
        commonUtils.saveActionDetail(auditId, CAMERA_CONDITION_TABLE, cameraCondition.getId(), null, cameraCondition);
    }

    @Override
    public List<String> getActiveProfile(Long cameraId) {
        List<String> activeProfileList = new ArrayList<>();

        List<CameraCondition> cameraConditionList = cameraConditionRepository.findByCameraIdAndStatus(cameraId, ACTIVE);

        cameraConditionList.forEach(cameraCondition -> activeProfileList.add(cameraCondition.getProfileCode()));

        return activeProfileList;
    }

    @Override
    public Optional<CameraCondition> getConditionByProfile(Long cameraId, String profileCode) {
        return cameraConditionRepository.findByCameraIdAndProfileCodeAndStatus(cameraId, profileCode, ACTIVE);
    }
}
