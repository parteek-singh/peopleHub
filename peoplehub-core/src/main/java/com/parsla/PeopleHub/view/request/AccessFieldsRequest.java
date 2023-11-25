package com.parsla.PeopleHub.view.request;

import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AccessFieldsRequest {
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


    int accessFieldNo1;
    int accessFieldNo2;
    int accessFieldNo3;
    int accessFieldNo4;
    int accessFieldNo5;
    int accessFieldNo6;
    int accessFieldNo7;
    int accessFieldNo8;
    int accessFieldNo9;
    int accessFieldNo10;


    Date accessFieldDate1;
    Date accessFieldDate2;
    Date accessFieldDate3;
    Date accessFieldDate4;
    Date accessFieldDate5;
    Date accessFieldDate6;
    Date accessFieldDate7;
    Date accessFieldDate8;
    Date accessFieldDate9;
    Date accessFieldDate10;
}
