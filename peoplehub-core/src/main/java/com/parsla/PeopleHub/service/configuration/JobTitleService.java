package com.parsla.PeopleHub.service.configuration;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.entity.configuration.JobTitle;
import com.parsla.PeopleHub.repo.configuration.JobTitleRepository;
import com.parsla.PeopleHub.view.request.JobTitleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobTitleService {

    private final JobTitleRepository jobTitleRepository;

    public JobTitleService(JobTitleRepository jobTitleRepository){
        this.jobTitleRepository = jobTitleRepository;
    }

    public JobTitle add(JobTitleRequest request) {

        System.out.println("JobTitleRequest Setting");
        JobTitle jobTitle = JobTitle.builder()
                .name(request.getName())
                .build();

        return jobTitleRepository.save(jobTitle);

    }

    public JobTitle update(JobTitleRequest request, long jobTitleId){
        JobTitle jobTitle = getByNo(jobTitleId);
        jobTitle.setName(request.getName());
        return jobTitleRepository.save(jobTitle);
    }

    public JobTitle getByNo(long jobTitleId) {
        Optional<JobTitle> jobTitle = jobTitleRepository.findByJobTitleId(jobTitleId);
        if (jobTitle.isPresent()) {
            return jobTitle.get();
        }

        throw new RuntimeException("JobTitle not found");
    }


    public long countAll() {
        return jobTitleRepository.count();
    }

    public List<JobTitle> getAll() {
        return  this.jobTitleRepository.findAll();
    }

    public List<JobTitle> getAll(Pageable pageable) {
        Page<JobTitle> page =  this.jobTitleRepository.findAll(pageable);
        return page.getContent();
    }
}
