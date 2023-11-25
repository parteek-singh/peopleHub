package com.parsla.PeopleHub.repo.configuration;

import com.parsla.PeopleHub.entity.configuration.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("jobTitleRepository")
public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    Optional<JobTitle> findByJobTitleId(long jobTitleId);
}
