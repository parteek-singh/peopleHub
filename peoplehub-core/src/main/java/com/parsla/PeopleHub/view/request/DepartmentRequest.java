package com.parsla.PeopleHub.view.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DepartmentRequest {

     String name;
     String location;
     String desc;
     Long compNo;

}
