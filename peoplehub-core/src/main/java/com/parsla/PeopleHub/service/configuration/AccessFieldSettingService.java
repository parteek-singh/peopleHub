package com.parsla.PeopleHub.service.configuration;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.entity.configuration.AccessFieldSetting;
import com.parsla.PeopleHub.repo.configuration.AccessFieldSettingRepository;
import com.parsla.PeopleHub.view.request.AccessFieldSettingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class AccessFieldSettingService {

    private final AccessFieldSettingRepository accessFieldSettingRepository;
    private final NumberUtil numberUtil;

    @Autowired
    public AccessFieldSettingService(AccessFieldSettingRepository accessFieldSettingRepository,
                                     NumberUtil numberUtil) {
        this.accessFieldSettingRepository = accessFieldSettingRepository;
        this.numberUtil = numberUtil;
    }

    public AccessFieldSetting add(AccessFieldSettingRequest request) {

        System.out.println("create Access Field Setting");
//        long count = accessFieldSettingRepository.count();
//        long accessFieldSettingId = numberUtil.generateIndexNo(ObjectType.ACCESS_FIELD, count);
        AccessFieldSetting documentType = getByType(request.getType());
        if (Objects.isNull(documentType)) {
            documentType = AccessFieldSetting.builder().build();
        }
        documentType.setType(request.getType());
        documentType.setAccFieldNoName1(request.getAccFieldNoName1());
        documentType.setAccFieldNoName2(request.getAccFieldNoName2());
        documentType.setAccFieldNoName3(request.getAccFieldNoName3());
        documentType.setAccFieldNoName4(request.getAccFieldNoName4());
        documentType.setAccFieldNoName5(request.getAccFieldNoName5());
        documentType.setAccFieldNoName6(request.getAccFieldNoName6());
        documentType.setAccFieldNoName7(request.getAccFieldNoName7());
        documentType.setAccFieldNoName8(request.getAccFieldNoName8());
        documentType.setAccFieldNoName9(request.getAccFieldNoName9());
        documentType.setAccFieldNoName10(request.getAccFieldNoName10());
        documentType.setAccFieldDateName1(request.getAccFieldDateName1());
        documentType.setAccFieldDateName2(request.getAccFieldDateName2());
        documentType.setAccFieldDateName3(request.getAccFieldDateName3());
        documentType.setAccFieldDateName4(request.getAccFieldDateName4());
        documentType.setAccFieldDateName5(request.getAccFieldDateName5());
        documentType.setAccFieldDateName6(request.getAccFieldDateName6());
        documentType.setAccFieldDateName7(request.getAccFieldDateName7());
        documentType.setAccFieldDateName8(request.getAccFieldDateName8());
        documentType.setAccFieldDateName9(request.getAccFieldDateName9());
        documentType.setAccFieldDateName10(request.getAccFieldDateName10());
        documentType.setAccFieldName1(request.getAccFieldName1());
        documentType.setAccFieldName2(request.getAccFieldName2());
        documentType.setAccFieldName3(request.getAccFieldName3());
        documentType.setAccFieldName4(request.getAccFieldName4());
        documentType.setAccFieldName5(request.getAccFieldName5());
        documentType.setAccFieldName6(request.getAccFieldName6());
        documentType.setAccFieldName7(request.getAccFieldName7());
        documentType.setAccFieldName8(request.getAccFieldName8());
        documentType.setAccFieldName9(request.getAccFieldName9());
        documentType.setAccFieldName10(request.getAccFieldName10());


        return accessFieldSettingRepository.save(documentType);

    }

    public AccessFieldSetting update(AccessFieldSettingRequest request, AFieldSettingType type) {
        AccessFieldSetting documentType = getByType(type);
        documentType.setType(request.getType());
        documentType.setAccFieldName1(request.getAccFieldName1());
        documentType.setAccFieldName2(request.getAccFieldName2());
        documentType.setAccFieldName3(request.getAccFieldName3());
        documentType.setAccFieldName4(request.getAccFieldName4());
        documentType.setAccFieldName5(request.getAccFieldName5());
        documentType.setAccFieldName6(request.getAccFieldName6());
        documentType.setAccFieldName7(request.getAccFieldName7());
        documentType.setAccFieldName8(request.getAccFieldName8());
        documentType.setAccFieldName9(request.getAccFieldName9());
        documentType.setAccFieldName10(request.getAccFieldName10());

        documentType.setAccFieldNoName1(request.getAccFieldNoName1());
        documentType.setAccFieldNoName2(request.getAccFieldNoName2());
        documentType.setAccFieldNoName3(request.getAccFieldNoName3());
        documentType.setAccFieldNoName4(request.getAccFieldNoName4());
        documentType.setAccFieldNoName5(request.getAccFieldNoName5());
        documentType.setAccFieldNoName6(request.getAccFieldNoName6());
        documentType.setAccFieldNoName7(request.getAccFieldNoName7());
        documentType.setAccFieldNoName8(request.getAccFieldNoName8());
        documentType.setAccFieldNoName9(request.getAccFieldNoName9());
        documentType.setAccFieldNoName10(request.getAccFieldNoName10());

        documentType.setAccFieldDateName1(request.getAccFieldDateName1());
        documentType.setAccFieldDateName2(request.getAccFieldDateName2());
        documentType.setAccFieldDateName3(request.getAccFieldDateName3());
        documentType.setAccFieldDateName4(request.getAccFieldDateName4());
        documentType.setAccFieldDateName5(request.getAccFieldDateName5());
        documentType.setAccFieldDateName6(request.getAccFieldDateName6());
        documentType.setAccFieldDateName7(request.getAccFieldDateName7());
        documentType.setAccFieldDateName8(request.getAccFieldDateName8());
        documentType.setAccFieldDateName9(request.getAccFieldDateName9());
        documentType.setAccFieldDateName10(request.getAccFieldDateName10());

        return accessFieldSettingRepository.save(documentType);
    }

    public AccessFieldSetting getByType(AFieldSettingType type) {

        try {

            Optional<AccessFieldSetting> accessFieldSetting = accessFieldSettingRepository.findByType(type);
            if (accessFieldSetting.isPresent()) {
                return accessFieldSetting.get();
            }

        } catch (Exception e) {
            log.error("Exception : AccessFieldSetting  not found" + e.getMessage());

        }
       return null;
    }

    public AccessFieldSetting getByNo(long accessFieldSettingId) {

        try {

            Optional<AccessFieldSetting> accessFieldSetting = accessFieldSettingRepository.findByAccessFieldSettingId(accessFieldSettingId);
            if (accessFieldSetting.isPresent()) {
                return accessFieldSetting.get();
            }

        } catch (Exception e) {
            log.error("Exception : AccessFieldSetting  not found" + e.getMessage());

        }
        throw new RuntimeException("AccessFieldSetting  not found");
    }


    public long countAll() {
        return accessFieldSettingRepository.count();
    }

    public List<AccessFieldSetting> getAll(Pageable pageable) {
        Page<AccessFieldSetting> page = this.accessFieldSettingRepository.findAll(pageable);
        return page.getContent();
    }
}
