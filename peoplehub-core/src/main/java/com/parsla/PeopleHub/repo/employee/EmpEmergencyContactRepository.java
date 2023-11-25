package com.parsla.PeopleHub.repo.employee;

import com.parsla.PeopleHub.entity.employee.EmpEmergencyContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("empEmergencyContactRepository")
public interface EmpEmergencyContactRepository extends JpaRepository<EmpEmergencyContact, Long> {
    Optional<EmpEmergencyContact> findByContactIdAndCompany_CompNo(long empNo, long compNo);

    List<EmpEmergencyContact> findAllByCompany_CompanyId(long companyId);

    Page<EmpEmergencyContact> findAllByCompany_CompNo(Pageable pageable, long compNo);
}
