package com.parsla.PeopleHub.view.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccessFieldView {

    String key;
    Object value;
}
