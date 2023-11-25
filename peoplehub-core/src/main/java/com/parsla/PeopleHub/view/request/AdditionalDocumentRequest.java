package com.parsla.PeopleHub.view.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdditionalDocumentRequest {
    long docNo;
    String label;
    String path;
    char[] data;
}
