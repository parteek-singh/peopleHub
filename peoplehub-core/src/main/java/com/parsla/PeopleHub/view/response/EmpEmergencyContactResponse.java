package com.parsla.PeopleHub.view.response;

import com.parsla.PeopleHub.constant.enums.Relationship;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmpEmergencyContactResponse {
    Long contactId;
    String name;
    Relationship relation;
    String email;
    String mobile;
    String address;
    Long compNo;
    Long empNo;
}
