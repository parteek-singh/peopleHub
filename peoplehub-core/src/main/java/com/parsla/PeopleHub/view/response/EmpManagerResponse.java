package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmpManagerResponse {
    long empNo;
    String name;
    String jobTitle;
    long managerNo;
}
