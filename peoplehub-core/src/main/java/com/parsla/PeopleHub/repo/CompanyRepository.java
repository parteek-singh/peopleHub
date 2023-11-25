package com.parsla.PeopleHub.repo;

import com.parsla.PeopleHub.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("companyRepository")
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompNo(long compNo);

}
