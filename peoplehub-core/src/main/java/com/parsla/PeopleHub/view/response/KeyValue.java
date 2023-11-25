package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeyValue {
    String key;
    Object value;
}
