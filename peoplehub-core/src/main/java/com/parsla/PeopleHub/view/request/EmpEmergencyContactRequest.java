package com.parsla.PeopleHub.view.request;

import com.parsla.PeopleHub.constant.enums.Relationship;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class EmpEmergencyContactRequest {
    String name;
    Relationship relation;
    String email;
    String mobile;
    String address;
    Long compNo;
    Long empNo;
}
