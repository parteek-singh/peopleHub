package com.parsla.PeopleHub.view.request;

import com.parsla.PeopleHub.constant.enums.GenderType;
import com.parsla.PeopleHub.constant.enums.PayCycleType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class EmployeeRequest {
    String fName;
    String mName;
    String lName;
    GenderType gender;
    Date dob;
    Date hireDate;
    Date lastDate;
    String email;
    String mobile;
    String address;
    String state;
    String country;
    String jobTitle;
    double baseSalary;
    PayCycleType payCycle;
    Long managerNo;
    Long acctNo;
    Long deptNo;
}
