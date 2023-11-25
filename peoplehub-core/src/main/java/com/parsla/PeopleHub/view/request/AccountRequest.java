package com.parsla.PeopleHub.view.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AccountRequest {
    String name;
    String description;
    Date startDt;
    long deptNo;
}
