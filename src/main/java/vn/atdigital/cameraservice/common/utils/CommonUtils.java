package vn.atdigital.cameraservice.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import vn.atdigital.cameraservice.domain.model.ActionAudit;
import vn.atdigital.cameraservice.domain.model.ActionDetail;
import vn.atdigital.cameraservice.repository.ActionAuditRepository;
import vn.atdigital.cameraservice.repository.ActionDetailRepository;
import vn.atdigital.cameraservice.repository.LookupValueRepository;

import java.time.LocalDateTime;
import java.util.Map;

import static vn.atdigital.cameraservice.common.Constants.TABLE_STATUS.ACTIVE;
import static vn.atdigital.cameraservice.common.utils.MessageUtils.getMessage;

@Component
@RequiredArgsConstructor
public class CommonUtils {
    private final ActionAuditRepository actionAuditRepository;
    private final ActionDetailRepository actionDetailRepository;
    private final LookupValueRepository lookupValueRepository;

    public static ObjectMapper objectMapper = new ObjectMapper();

    public Long saveActionAudit(String userName, String actionCode, Long pkId, String pkType) {
        ActionAudit audit = ActionAudit.builder()
                .issueDateTime(LocalDateTime.now())
                .actionCode(actionCode)
                .username(userName)
                .pkId(pkId)
                .pkType(pkType)
                .build();
        audit = actionAuditRepository.save(audit);
        return audit.getId();
    }

    public void saveActionDetail(Long auditId,
                                 String tableName,
                                 Long pkId,
                                 Object oldValue,
                                 Object newValue) {

        JsonNode oldNode = (oldValue == null ? null : objectMapper.valueToTree(oldValue));
        JsonNode newNode = (newValue == null ? null : objectMapper.valueToTree(newValue));

        ActionDetail detail = ActionDetail.builder()
                .auditId(auditId)
                .issueDateTime(LocalDateTime.now())
                .tableName(tableName)
                .pkId(pkId)
                .oldValue(oldNode)
                .newValue(newNode)
                .build();

        actionDetailRepository.save(detail);
    }

    public void checkCodeExist(String code) {
        Assert.isTrue(lookupValueRepository.existsByCodeAndStatus(code, ACTIVE), getMessage("0001.lookup-value.null-or-empty", code));
    }

    public static void checkCodeExists(String code, Map<String, String> lookupValueMap) {
        String typeValue = lookupValueMap.get(code);
        if (typeValue == null) throw new EntityNotFoundException(getMessage("0001.lookup-value.null-or-empty", code));
    }

    public static void validateIpV4(String ip) {
        // Regex IPv4 chuẩn: 0–255 cho mỗi octet
        String ipv4Regex =
                "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                        + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";

        Assert.isTrue(ip.matches(ipv4Regex), getMessage("0001.tcpip.ipv4.wrong-format", ip));
    }

    public static void validateIpV6(String ip) {
        String ipv6Regex =
                "^(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}"
                        + "|(([0-9a-fA-F]{1,4}:){1,7}:)"
                        + "|(([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4})"
                        + "|(([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2})"
                        + "|(([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3})"
                        + "|(([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4})"
                        + "|(([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5})"
                        + "|([0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6}))"
                        + "|(:((:[0-9a-fA-F]{1,4}){1,7}|:)))$";

        Assert.isTrue(ip.matches(ipv6Regex), getMessage("0004.tcpip.ipv6.wrong-format", ip));
    }

    public static void validateCidrNotation(String cidrNotation) {
        int cidrNotationInt = Integer.parseInt(cidrNotation);
        Assert.isTrue(cidrNotationInt >= 0 && cidrNotationInt <= 128,
                getMessage("0005.tcpip.cidr.wrong-format", cidrNotationInt)
        );
    }

    public static void validateMacAddress(String mac) {
        // Regex MAC address chuẩn (XX:XX:XX:XX:XX:XX hoặc XX-XX-XX-XX-XX-XX)
        String macRegex =
                "^([0-9A-Fa-f]{2}([:-])){5}([0-9A-Fa-f]{2})$";

        Assert.isTrue(mac.matches(macRegex), getMessage("0003.tcpip.mac-address.wrong-format", mac));
    }

    public static void validateInRange(Integer value, Integer min, Integer max) {
        if (max == null) Assert.isTrue(value >= min, getMessage("0001.common.number.not-in-range", value));
        else Assert.isTrue(value >= min && value <= max, getMessage("0001.common.number.not-in-range", value));
    }
}
