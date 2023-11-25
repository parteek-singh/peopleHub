package com.parsla.PeopleHub.view.response;

import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccessFieldsResponse {
    long accessFieldId;
    AFieldSettingType type;
    String accessField1;
    String accessField2;
    String accessField3;
    String accessField4;
    String accessField5;
    String accessField6;
    String accessField7;
    String accessField8;
    String accessField9;
    String accessField10;
}
