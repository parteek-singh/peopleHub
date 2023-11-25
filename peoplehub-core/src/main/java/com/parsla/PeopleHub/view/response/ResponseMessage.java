package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseMessage {
    private String message;

    private String errorCode;

    private Boolean error;

    private Object entity;
}
