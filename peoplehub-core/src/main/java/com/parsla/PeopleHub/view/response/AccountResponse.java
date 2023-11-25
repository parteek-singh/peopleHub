package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AccountResponse {
    long id;
    String name;
    String description;
    Date startDt;
    DepartmentResponse department;
    long compNo;
}
