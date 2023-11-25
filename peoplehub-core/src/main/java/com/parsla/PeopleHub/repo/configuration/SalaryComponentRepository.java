package com.parsla.PeopleHub.repo.configuration;

import com.parsla.PeopleHub.entity.configuration.SalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("salaryComponentRepository")
public interface SalaryComponentRepository extends JpaRepository<SalaryComponent, Long> {
    Optional<SalaryComponent> findBySalCompId(long salCompId);


    List<SalaryComponent> findAllByActive(boolean active);
}
