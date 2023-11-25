package com.parsla.PeopleHub.service.employee;

import com.parsla.PeopleHub.entity.Company;
import com.parsla.PeopleHub.entity.employee.EmpEmergencyContact;
import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.repo.employee.EmpEmergencyContactRepository;
import com.parsla.PeopleHub.service.CompanyService;
import com.parsla.PeopleHub.view.request.EmpEmergencyContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpEmergencyContactService {

    private final EmpEmergencyContactRepository empEmergencyContactRepository;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Autowired
    public EmpEmergencyContactService(EmpEmergencyContactRepository empEmergencyContactRepository,
                                      EmployeeService employeeService,
                                      CompanyService companyService){
        this.empEmergencyContactRepository = empEmergencyContactRepository;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }


    public EmpEmergencyContact add(EmpEmergencyContactRequest request) {

        System.out.println("create Emp Emergency Contact Request");
        Company company = companyService.getByNo(request.getCompNo());
        Employee employee  = employeeService.getByNo(request.getEmpNo());

        EmpEmergencyContact emergencyContact = EmpEmergencyContact.builder()
                .name(request.getName())
                .relation(request.getRelation())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .employee(employee)
                .company(company)
                .build();

        return empEmergencyContactRepository.save(emergencyContact);

    }

    public EmpEmergencyContact update(EmpEmergencyContactRequest request, long contactId, long compNo){
        EmpEmergencyContact emergencyContact = getByNo(contactId, compNo);
        emergencyContact.setName(request.getName());
        emergencyContact.setRelation(request.getRelation());
        emergencyContact.setEmail(request.getEmail());
        emergencyContact.setMobile(request.getMobile());
        emergencyContact.setAddress(request.getAddress());
        return empEmergencyContactRepository.save(emergencyContact);
    }

    public EmpEmergencyContact getByNo(long contactId, long compNo) {
        Optional<EmpEmergencyContact> project = empEmergencyContactRepository.findByContactIdAndCompany_CompNo(contactId, compNo);
        if (project.isPresent()) {
            return project.get();
        }

        throw new RuntimeException("Employee not found");
    }

    public List<EmpEmergencyContact> getAll(long companyId){
        return empEmergencyContactRepository.findAllByCompany_CompanyId(companyId);
    }

    public boolean updateManager(long deptNo, long empNo) {
        return true;
    }

    public long countAll() {
        return empEmergencyContactRepository.count();
    }

    public List<EmpEmergencyContact> getAll(Pageable pageable, long compNo) {
        Page<EmpEmergencyContact> page =  this.empEmergencyContactRepository.findAllByCompany_CompNo(pageable, compNo);
        return page.getContent();
    }

}
