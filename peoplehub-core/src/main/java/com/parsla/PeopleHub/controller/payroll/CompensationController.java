package com.parsla.PeopleHub.controller.payroll;

import com.parsla.PeopleHub.entity.Account;
import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.service.employee.EmployeeService;
import com.parsla.PeopleHub.service.payroll.CompensationService;
import com.parsla.PeopleHub.view.request.AccountRequest;
import com.parsla.PeopleHub.view.request.BaseSalaryUpdateRequest;
import com.parsla.PeopleHub.view.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("v1/emp/compensation")
public class CompensationController {

    private final CompensationService compensationService;
    private final EmployeeService employeeService;

    @Autowired
    public CompensationController(CompensationService compensationService,
                                  EmployeeService employeeService){
        this.compensationService = compensationService;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/baseSal",method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> update(@RequestBody BaseSalaryUpdateRequest request) {
        try {
            this.employeeService.updateBaseSalary(request);
            //Map<String,  Map<String, Double>> compensation= compensationService.getCompensation(request.getEmpNo());

            return ResponseEntity.ok("Base Salary updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update Base Salary. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<List<KeyValue>> getCompensation(@RequestParam(value = "empNo" ) long empNo) {
        try {
            List<KeyValue> compensation= compensationService.getCompensation(empNo);
            return ResponseEntity.status(HttpStatus.OK).body( compensation);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
