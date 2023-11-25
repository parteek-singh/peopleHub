package com.parsla.PeopleHub.view.response;

import com.parsla.PeopleHub.entity.employee.Employee;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DepartmentListView {
    private long id;
    private String name;
    private String location;
    private String description;
    EmpManagerResponse managerResponse;

}
