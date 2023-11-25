package com.parsla.PeopleHub.service.payroll;

import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.entity.payroll.PayslipGenerationLog;
import com.parsla.PeopleHub.repo.payroll.PayslipGenerationLogRepository;
import com.parsla.PeopleHub.service.employee.EmployeeService;
import com.parsla.PeopleHub.view.request.PayslipGenerationLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayslipGenerationLogService {

    private final PayslipGenerationLogRepository repository;
    private final EmployeeService employeeService;

    @Autowired
    public PayslipGenerationLogService(PayslipGenerationLogRepository repository,
                                       EmployeeService employeeService) {
        this.repository = repository;
        this.employeeService = employeeService;
    }


    public void generatePaySlips(PayslipGenerationLogRequest request){
        System.out.println("create Payslip Generation Log");

        // Check if salary for index is already created
        // check if employee daily rate

        List<Employee> employees = employeeService.getAll();
        employees.forEach( employee -> {
            System.out.println("Slaary calaculated");
           // add(request, employee);
        });

    }

    private PayslipGenerationLog add(PayslipGenerationLogRequest request, Employee employee) {

        PayslipGenerationLog salaryComponent = PayslipGenerationLog.builder()
//                .index(request.getIndex())
//                .year(request.getYear())
//                .generationDate(request.getGenerationDate())
//                .payslipData("Data logic in progress")
                //.employee(employee)
                .build();
        return repository.save(salaryComponent);

    }


    public PayslipGenerationLog getByNo(long payId) {
        Optional<PayslipGenerationLog> component = repository.findByPayGenId(payId);
        if (component.isPresent()) {
            return component.get();
        }

        throw new RuntimeException("Payslip Generation Log  not found");
    }

    public List<PayslipGenerationLog> getAll() {
        return repository.findAll();
    }

    public long countAll() {
        return repository.count();
    }

    public List<PayslipGenerationLog> getAll(Pageable pageable) {
        Page<PayslipGenerationLog> page = this.repository.findAll(pageable);
        return page.getContent();
    }
}
