package com.parsla.PeopleHub.service.employee;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.constant.enums.RoleEnum;
import com.parsla.PeopleHub.entity.*;
import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.projection.SelectEmployee;
import com.parsla.PeopleHub.repo.employee.EmployeeRepository;
import com.parsla.PeopleHub.service.AccountService;
import com.parsla.PeopleHub.service.CompanyService;
import com.parsla.PeopleHub.service.DepartmentService;
import com.parsla.PeopleHub.service.RoleService;
import com.parsla.PeopleHub.service.authconfig.UserInfoService;
import com.parsla.PeopleHub.view.request.AssignManagerRequest;
import com.parsla.PeopleHub.view.request.BaseSalaryUpdateRequest;
import com.parsla.PeopleHub.view.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    private final UserInfoService userInfoService;
    private final AccountService accountService;
    private final RoleService roleService;
    private final NumberUtil numberUtil;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           RoleService roleService,
                           AccountService accountService,
                           DepartmentService departmentService,
                           UserInfoService userInfoService,
                           NumberUtil numberUtil) {
        this.employeeRepository = employeeRepository;
        this.roleService = roleService;
        this.accountService = accountService;
        this.departmentService = departmentService;
        this.userInfoService = userInfoService;
        this.numberUtil = numberUtil;
    }

    @Transactional
    public Employee add(EmployeeRequest request) {

        System.out.println("create  Employee");
        Department department = departmentService.getByNo(request.getDeptNo());
        Account account = accountService.getByNo(request.getAcctNo());
        Employee manager = null;
        if (!Objects.isNull(request.getManagerNo())) {
            manager = getByNo(request.getManagerNo());
        }

        Roles role = roleService.findRoleByName(RoleEnum.EMP.name());

        long count = employeeRepository.count();
        long empNo = numberUtil.generateIndexNo(ObjectType.EMPLOYEE, count);


        LocalDate localDate = new java.sql.Date(request.getDob().getTime()).toLocalDate();
        String defaultPassword = String.format("%02d", localDate.getDayOfMonth()) + String.format("%02d", localDate.getMonthValue()) + localDate.getYear();

        Employee employeeObj = Employee.builder()
                .empNo(empNo)
                .fName(request.getFName())
                .mName(request.getMName())
                .lName(request.getLName())
                .gender(request.getGender())
                .dob(request.getDob()).hireDt(request.getHireDate()).lastDt(request.getLastDate())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .state(request.getState())
                .country(request.getCountry())
                .jobTitle(request.getJobTitle())
                .baseSalary(request.getBaseSalary())
                .payCycle(request.getPayCycle())
                .account(account)
                .department(department)
                .manager(manager)
                .build();

        Employee employee = employeeRepository.save(employeeObj);
        UserInfo userInfo = UserInfo.builder()
                .email(request.getEmail())
                .password(defaultPassword)
                .employee(employee)
                .build();
        userInfoService.addUser(userInfo);

        return employee;

    }

    public Employee update(EmployeeRequest request, long empNo) {
        Employee account = getByNo(empNo);
        account.setFName(request.getFName());
        account.setMName(request.getMName());
        account.setLName(request.getLName());
        account.setGender(request.getGender());
        account.setDob(request.getDob());
        account.setHireDt(request.getHireDate());
        account.setEmail(request.getEmail());
        account.setMobile(request.getMobile());
        account.setAddress(request.getAddress());
        return employeeRepository.save(account);
    }

    public Employee updateBaseSalary(BaseSalaryUpdateRequest request) {
        Employee account = getByNo(request.getEmpNo());
        account.setBaseSalary(request.getBaseSalary());
        account.setPayCycle(request.getPayCycle());
        return employeeRepository.save(account);
    }

    public Employee getByNo(long empNo) {
        Optional<Employee> project = employeeRepository.findByEmpNo(empNo);
        if (project.isPresent()) {
            return project.get();
        }

        throw new RuntimeException("Employee not found");
    }

    public List<SelectEmployee> getAllForSelect(Pageable pageable) {
        Page<SelectEmployee> page = this.employeeRepository.findAllSelectEmp(pageable);
        return page.getContent();
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public long countAll() {
        return employeeRepository.count();
    }

    public List<Employee> getAll(Pageable pageable) {
        Page<Employee> page = this.employeeRepository.findAll(pageable);
        return page.getContent();
    }

    public void assignManager(AssignManagerRequest request) {
        List<Employee> employees = employeeRepository.findByEmpNoIn(Arrays.asList(request.getEmpNo(), request.getManNo()));
        Employee employee = null;
        Employee manager = null;
        for (Employee emp :
                employees) {
            if (emp.getEmpNo() == request.getEmpNo()) {
                employee = emp;
            } else {
                manager = emp;
            }
        }

        assert employee != null;
        employee.setManager(manager);
        employeeRepository.save(employee);
    }
}
