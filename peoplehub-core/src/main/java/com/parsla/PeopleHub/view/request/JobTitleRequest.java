package com.parsla.PeopleHub.view.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobTitleRequest {
    long id;
    String name;
}
