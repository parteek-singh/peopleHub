package com.parsla.PeopleHub.controller;

import com.parsla.PeopleHub.entity.Account;
import com.parsla.PeopleHub.entity.Department;
import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.service.AccountService;
import com.parsla.PeopleHub.view.request.AccountRequest;
import com.parsla.PeopleHub.view.request.DepartmentRequest;
import com.parsla.PeopleHub.view.response.AccountListView;
import com.parsla.PeopleHub.view.response.AccountResponse;
import com.parsla.PeopleHub.view.response.DepartmentResponse;
import com.parsla.PeopleHub.view.response.EmpManagerResponse;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("v1/acct")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> create(@RequestBody AccountRequest request) {
        try {
            this.accountService.add(request);
            return ResponseEntity.ok("Account added successfully");
        } catch (Exception e) {
            log.error("EXCEPgetCompanyByCompNoTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add Account. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> update(@RequestBody AccountRequest request, @RequestParam(value = "acctNo") Long acctNo) {
        try {
            this.accountService.update(request, acctNo);
            return ResponseEntity.ok("Account updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update Account. Please contact support");
        }
    }

    @RequestMapping( method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<AccountResponse> get(@RequestParam(value = "acctNo" ) long acctNo) {
        try {
            Account account = this.accountService.getByNo(acctNo);
            Department department = account.getDepartment();
            DepartmentResponse departmentResponse = DepartmentResponse.builder()
                    .id(department.getDeptNo())
                    .name(department.getName()).build();

            AccountResponse response = AccountResponse.builder()
                    .id(acctNo)
                    .name(account.getName())
                    .description(account.getDescription())
                    .startDt(account.getStartDt())
                    .department(departmentResponse)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body( response);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<Map<String, Object>> getList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "sort", defaultValue = "0") Integer sortBy,
                                                                 @RequestParam(value = "sortCol", defaultValue = "name") String sortCol) {
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

            long totalCount = this.accountService.countAll();
            List<Account> accounts = this.accountService.getAll(pageable);
            List<AccountListView> accountListViews = new ArrayList<>();
            accounts.forEach(account -> {
                Employee employee= account.getManager();

                Department department= account.getDepartment();

                EmpManagerResponse managerResponse = null;
                Long managerEmpNo = null;
                String managerName = null;
                Long deptNo = null;
                String deptName = null;
                if(Objects.nonNull(employee)){
                    managerEmpNo = employee.getEmpNo();
                    managerName = String.join(" ",employee.getFName(), employee.getMName(), employee.getLName());
                }
                DepartmentResponse departmentResponse = null ;
                if(Objects.nonNull(department)){
                    deptNo = department.getDeptNo();
                    deptName = department.getName();
                }


                AccountListView accountListView = AccountListView.builder()
                        .id(account.getAcctNo()).name(account.getName()).startDt(account.getStartDt()).description(account.getDescription())
                        .managerName(managerName)
                        .managerEmpNo(managerEmpNo)
                        .deptNo(deptNo)
                        .deptName(deptName)
                        .build();
                accountListViews.add(accountListView);
            });

            Map<String, Object> map = new HashMap<>();
            map.put("data", accountListViews);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
