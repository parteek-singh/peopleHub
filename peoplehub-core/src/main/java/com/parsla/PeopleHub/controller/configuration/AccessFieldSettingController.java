package com.parsla.PeopleHub.controller.configuration;

import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import com.parsla.PeopleHub.entity.configuration.AccessFieldSetting;
import com.parsla.PeopleHub.service.configuration.AccessFieldSettingService;
import com.parsla.PeopleHub.view.request.AccessFieldSettingRequest;
import com.parsla.PeopleHub.view.response.AccessFieldSettingResponse;
import com.parsla.PeopleHub.view.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("v1/hrman/afSetting")
public class AccessFieldSettingController {

    private final AccessFieldSettingService empAccessFieldService;

    @Autowired
    public AccessFieldSettingController(AccessFieldSettingService empAccessFieldService) {
        this.empAccessFieldService = empAccessFieldService;
    }


    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<ResponseMessage> createOrUpdate(@RequestBody AccessFieldSettingRequest request) {
        try {
            if (Objects.isNull(request.getType())) {
                throw new RuntimeException("Invalid request");
            }
            this.empAccessFieldService.add(request);
            ResponseMessage responseMessage = ResponseMessage.builder().message("Updated successfully").build();
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            ResponseMessage responseMessage = ResponseMessage.builder().message("Not able to add Account. Please contact support").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<AccessFieldSettingResponse> get(@RequestParam(value = "type") AFieldSettingType type) {
        try {
            AccessFieldSetting accessFieldSetting = this.empAccessFieldService.getByType(type);
            if (!accessFieldSetting.getType().equals(type)) {
                throw new RuntimeException("accessFieldSetting and type mismatched");
            }

            AccessFieldSettingResponse response = AccessFieldSettingResponse.builder()
                    .accessFieldSettingId(accessFieldSetting.getAccessFieldSettingId())
                    .type(accessFieldSetting.getType())
                    .accFieldNoName1(accessFieldSetting.getAccFieldNoName1())
                    .accFieldNoName2(accessFieldSetting.getAccFieldNoName2())
                    .accFieldNoName3(accessFieldSetting.getAccFieldNoName3())
                    .accFieldNoName4(accessFieldSetting.getAccFieldNoName4())
                    .accFieldNoName5(accessFieldSetting.getAccFieldNoName5())
                    .accFieldNoName6(accessFieldSetting.getAccFieldNoName6())
                    .accFieldNoName7(accessFieldSetting.getAccFieldNoName7())
                    .accFieldNoName8(accessFieldSetting.getAccFieldNoName8())
                    .accFieldNoName9(accessFieldSetting.getAccFieldNoName9())
                    .accFieldNoName10(accessFieldSetting.getAccFieldNoName10())
                    .accFieldDateName1(accessFieldSetting.getAccFieldDateName1())
                    .accFieldDateName2(accessFieldSetting.getAccFieldDateName2())
                    .accFieldDateName3(accessFieldSetting.getAccFieldDateName3())
                    .accFieldDateName4(accessFieldSetting.getAccFieldDateName4())
                    .accFieldDateName5(accessFieldSetting.getAccFieldDateName5())
                    .accFieldDateName6(accessFieldSetting.getAccFieldDateName6())
                    .accFieldDateName7(accessFieldSetting.getAccFieldDateName7())
                    .accFieldDateName8(accessFieldSetting.getAccFieldDateName8())
                    .accFieldDateName9(accessFieldSetting.getAccFieldDateName9())
                    .accFieldDateName10(accessFieldSetting.getAccFieldDateName10())
                    .accFieldName1(accessFieldSetting.getAccFieldName1())
                    .accFieldName2(accessFieldSetting.getAccFieldName2())
                    .accFieldName3(accessFieldSetting.getAccFieldName3())
                    .accFieldName4(accessFieldSetting.getAccFieldName4())
                    .accFieldName5(accessFieldSetting.getAccFieldName5())
                    .accFieldName6(accessFieldSetting.getAccFieldName6())
                    .accFieldName7(accessFieldSetting.getAccFieldName7())
                    .accFieldName8(accessFieldSetting.getAccFieldName8())
                    .accFieldName9(accessFieldSetting.getAccFieldName9())
                    .accFieldName10(accessFieldSetting.getAccFieldName10())
                    .build();


            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
