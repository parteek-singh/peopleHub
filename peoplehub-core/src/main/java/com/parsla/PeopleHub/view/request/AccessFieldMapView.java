package com.parsla.PeopleHub.view.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class AccessFieldMapView {
    Map<String, AccessFieldView> fields;
}
