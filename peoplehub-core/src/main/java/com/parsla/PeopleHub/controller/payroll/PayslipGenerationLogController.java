package com.parsla.PeopleHub.controller.payroll;

import com.parsla.PeopleHub.service.configuration.SalaryComponentService;
import com.parsla.PeopleHub.service.payroll.PayslipGenerationLogService;
import com.parsla.PeopleHub.view.request.PayslipGenerationLogRequest;
import com.parsla.PeopleHub.view.request.SalaryComponentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/hrman/paygen")
public class PayslipGenerationLogController {


    private final PayslipGenerationLogService payslipGenerationLogService;

    @Autowired
    public PayslipGenerationLogController(PayslipGenerationLogService payslipGenerationLogService){
        this.payslipGenerationLogService = payslipGenerationLogService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> createPayslips(@RequestBody PayslipGenerationLogRequest request) {
        try {

            this.payslipGenerationLogService.generatePaySlips(request);
            return ResponseEntity.ok("JobTitle added successfully");
        } catch (Exception e) {
            log.error("EXCEPgetCompanyByCompNoTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add Payslips. Please contact support");
        }
    }
}
