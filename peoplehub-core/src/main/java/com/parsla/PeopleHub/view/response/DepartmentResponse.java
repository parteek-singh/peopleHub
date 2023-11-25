package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DepartmentResponse {
    long id;
    String name;
    String location;
    String desc;
}
