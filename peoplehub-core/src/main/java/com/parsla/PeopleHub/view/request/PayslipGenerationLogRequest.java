package com.parsla.PeopleHub.view.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PayslipGenerationLogRequest {
    String index;
    short year;
    Date generationDate;
//    String payslipData;
//    long empNo;
}
