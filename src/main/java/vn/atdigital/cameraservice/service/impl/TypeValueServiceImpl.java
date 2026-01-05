package vn.atdigital.cameraservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.atdigital.cameraservice.domain.DTO.TypeValueMapDTO;
import vn.atdigital.cameraservice.domain.model.LookupType;
import vn.atdigital.cameraservice.domain.model.LookupValue;
import vn.atdigital.cameraservice.repository.LookupTypeRepository;
import vn.atdigital.cameraservice.repository.LookupValueRepository;
import vn.atdigital.cameraservice.service.TypeValueService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeValueServiceImpl implements TypeValueService {
    private final LookupTypeRepository lookupTypeRepository;
    private final LookupValueRepository lookupValueRepository;

    @Override
    public List<TypeValueMapDTO> getTypeCodeMap() {
        List<LookupType> lookupTypeList = lookupTypeRepository.findAll();
        List<LookupValue> lookupValueList = lookupValueRepository.findByIsActive(true);

        Map<Long, List<LookupValue>> valueMapByTypeId  = lookupValueList.stream().collect(Collectors.groupingBy(LookupValue::getLookupTypeId));

        return lookupTypeList.stream()
                .map(lookupType -> TypeValueMapDTO.builder()
                        .type(lookupType.getName())
                        .valueMapList(valueMapByTypeId.getOrDefault(lookupType.getId(), Collections.emptyList())
                                .stream()
                                .map(lookupValue -> Map.of(lookupValue.getCode(), lookupValue.getName()))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
