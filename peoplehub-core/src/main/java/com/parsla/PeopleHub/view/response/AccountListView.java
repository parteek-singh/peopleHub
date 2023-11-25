package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AccountListView {
    private long id;
    private String name;
    private String description;
    private Date startDt;

    private Long deptNo;
    private String deptName;
    private Long managerEmpNo;
    private String managerName;

}
