package com.parsla.PeopleHub.repo.payroll;

import com.parsla.PeopleHub.entity.payroll.PayslipGenerationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("payslipGenerationLogRepository")
public interface PayslipGenerationLogRepository extends JpaRepository<PayslipGenerationLog, Long> {
    Optional<PayslipGenerationLog> findByPayGenId(long payGenId);
}
