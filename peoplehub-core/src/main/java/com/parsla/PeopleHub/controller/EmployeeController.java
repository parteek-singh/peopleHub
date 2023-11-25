package com.parsla.PeopleHub.controller;

import com.parsla.PeopleHub.entity.Account;
import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.projection.SelectEmployee;
import com.parsla.PeopleHub.service.employee.EmployeeService;
import com.parsla.PeopleHub.view.request.AssignManagerRequest;
import com.parsla.PeopleHub.view.request.BaseSalaryUpdateRequest;
import com.parsla.PeopleHub.view.request.EmployeeRequest;
import com.parsla.PeopleHub.view.response.AccountResponse;
import com.parsla.PeopleHub.view.response.DepartmentResponse;
import com.parsla.PeopleHub.view.response.EmpManagerResponse;
import com.parsla.PeopleHub.view.response.EmployeeResponse;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("v1/emp")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> create(@RequestBody EmployeeRequest request) {
        try {
            this.employeeService.add(request);
            return ResponseEntity.ok("Employee added successfully");
        } catch (Exception e) {
            log.error("EXCEPgetCompanyByCompNoTION : " + e.getMessage());
            return ResponseEntity.internalServerError().body("Not able to add Employee. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')  or hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> update(@RequestBody EmployeeRequest request, @RequestParam(value = "empNo") Long empNo) {
        try {
            this.employeeService.update(request, empNo);
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update Employee. Please contact support");
        }
    }

    @RequestMapping(value = "/compensation", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')  or hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> updateCompensation(@RequestBody BaseSalaryUpdateRequest request) {
        try {
            this.employeeService.updateBaseSalary(request);
            return ResponseEntity.ok("Employee compensation updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update compensation.  Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<EmployeeResponse> getByHr(@RequestParam(value = "empNo" ) long empNo) {
        try {
            Employee employee = this.employeeService.getByNo(empNo);
            Account account = employee.getAccount();

            Employee manager = employee.getManager();

            EmpManagerResponse managerResponse = null;
            if(!Objects.isNull(manager)) {
                managerResponse = EmpManagerResponse.builder()
                        .empNo(manager.getEmpNo())
                        .name(String.join(" ", manager.getFName(), manager.getMName(), manager.getLName()))
                        .jobTitle(manager.getJobTitle()).build();
            }

            DepartmentResponse departmentResponse = DepartmentResponse.builder()
                    .name(employee.getDepartment().getName()).build();

            AccountResponse accountResponse = AccountResponse.builder()
                    .id(account.getAcctNo())
                    .name(account.getName())
                    .startDt(account.getStartDt())
                    .department(departmentResponse)
                    .build();



            EmployeeResponse response = EmployeeResponse.builder()
                    .empNo(employee.getEmpNo())
                    .fName(employee.getFName()).mName(employee.getMName()).lName(employee.getLName())
                    .gender(employee.getGender())
                    .dob(employee.getDob()).hireDate(employee.getHireDt()).lastDate(employee.getLastDt())
                    .email(employee.getEmail()).mobile(employee.getMobile())
                    .address(employee.getAddress()).state(employee.getState()).country(employee.getCountry())
                    .jobTitle(employee.getJobTitle())
                    .baseSalary(employee.getBaseSalary())
                    .payCycle(employee.getPayCycle())
                    .manager(managerResponse)
                    .account(accountResponse)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body( response);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "sort", defaultValue = "0") Integer sortBy,
                                                       @RequestParam(value = "sortCol", defaultValue = "fName") String sortCol) {
        try {
            int pageSize = 10;
            Pageable pageable = null;

            Sort sort = null;
            if (StringUtils.isNotBlank(sortCol)) {
                sort = Sort.by(sortCol);
                if (sortBy == 1) {
                    sort.descending();
                } else {
                    sort.ascending();
                }
                pageable = PageRequest.of(page, pageSize, sort);
            } else {
                pageable = PageRequest.of(page, pageSize);
            }

            long totalCount = this.employeeService.countAll();
            List<Employee> employees = this.employeeService.getAll(pageable);

            Map<String, Object> map = new HashMap<>();
            map.put("data", employees);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/assignMan", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> assignManager(@RequestBody AssignManagerRequest request) {
        try {
            this.employeeService.assignManager(request);
            log.info("Employee Manger updated");
            return ResponseEntity.ok("Employee Manager updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update Employee. Please contact support");
        }
    }

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<Map<String, Object>> getEmpListForSelect(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "sort", defaultValue = "0") Integer sortBy,
                                                     @RequestParam(value = "sortCol", defaultValue = "fName") String sortCol) {
        try {
            int pageSize = 10;
            Pageable pageable = null;

            Sort sort = null;
            if (StringUtils.isNotBlank(sortCol)) {
                sort = Sort.by(sortCol);
                if (sortBy == 1) {
                    sort.descending();
                } else {
                    sort.ascending();
                }
                pageable = PageRequest.of(page, pageSize, sort);
            } else {
                pageable = PageRequest.of(page, pageSize);
            }

            long totalCount = this.employeeService.countAll();
            List<SelectEmployee> employees = this.employeeService.getAllForSelect(pageable);

            Map<String, Object> map = new HashMap<>();
            map.put("data", employees);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
