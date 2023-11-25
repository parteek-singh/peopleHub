package com.parsla.PeopleHub.repo.configuration;

import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import com.parsla.PeopleHub.entity.configuration.AccessFieldSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("accessFieldSettingRepository")
public interface AccessFieldSettingRepository extends JpaRepository<AccessFieldSetting, Long> {
    Optional<AccessFieldSetting> findByAccessFieldSettingId(long accessFieldSettingId);

    Optional<AccessFieldSetting> findByType(AFieldSettingType type);
}
