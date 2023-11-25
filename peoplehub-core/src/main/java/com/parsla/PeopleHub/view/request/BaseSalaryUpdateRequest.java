package com.parsla.PeopleHub.view.request;

import com.parsla.PeopleHub.constant.enums.PayCycleType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseSalaryUpdateRequest {
    long empNo;
    double baseSalary;
    PayCycleType payCycle;
}
