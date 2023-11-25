package com.parsla.PeopleHub.view.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdditionalDocumentResponse {
    long docNo;
    String label;
}
