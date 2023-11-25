package com.parsla.PeopleHub.repo.employee;

import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.projection.SelectEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//    Optional<Employeeyee> findByEmpNoAndCompany_CompNo(long empNo, long compNo);

//    List<Employee> findAllByCompany_CompanyId(long companyId);
//
//    Page<Employee> findAllByCompany_CompNo(Pageable pageable, long compNo);

    Optional<Employee> findByEmpNo(long empNo);

    List<Employee> findByEmpNoIn(Iterable<Long> empNos);


    @Query(value = "FROM Employee")
    Page<SelectEmployee> findAllSelectEmp(Pageable pageable);
}
