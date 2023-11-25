package com.parsla.PeopleHub.view.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompanyRequest {

    String name;
    String location;
}
