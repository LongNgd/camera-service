package vn.atdigital.cameraservice.service;

import vn.atdigital.cameraservice.domain.DTO.TypeValueMapDTO;

import java.util.List;

public interface TypeValueService {
    List<TypeValueMapDTO> getTypeCodeMap();
}
