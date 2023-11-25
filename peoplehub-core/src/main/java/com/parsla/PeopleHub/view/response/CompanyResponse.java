package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompanyResponse {
    String name;
    String location;
    long compNo;
}
