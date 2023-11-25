package com.parsla.PeopleHub.repo.employee;

import com.parsla.PeopleHub.entity.employee.AccessFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("accessFieldsRepository")
public interface AccessFieldsRepository  extends JpaRepository<AccessFields, Long> {
//    Page<AccessFields> findAllByCompany_CompNo(Pageable pageable, long compNo);

    Optional<AccessFields> findByAccessFieldId(long accessFieldId);
    Optional<AccessFields> findByEmployee_EmpNo(long empNo);

//    Optional<AccessFields> findByAccessFieldIdAndCompany_CompNo(long accessFieldId, long compNo);
}
