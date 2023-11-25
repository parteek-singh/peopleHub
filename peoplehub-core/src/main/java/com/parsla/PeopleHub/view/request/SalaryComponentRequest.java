package com.parsla.PeopleHub.view.request;

import com.parsla.PeopleHub.constant.enums.Operations;
import com.parsla.PeopleHub.constant.enums.SalaryComponentType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SalaryComponentRequest {
    SalaryComponentType type;
    String name;
    boolean active;
    boolean included;
    boolean complex;
    String value;
    String component;
    Operations operation;
}
