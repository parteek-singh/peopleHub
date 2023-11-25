package com.parsla.PeopleHub.controller.configuration;

import com.parsla.PeopleHub.entity.configuration.JobTitle;
import com.parsla.PeopleHub.service.configuration.JobTitleService;
import com.parsla.PeopleHub.view.request.JobTitleRequest;
import com.parsla.PeopleHub.view.response.JobTitleResponse;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("v1/hrman/jobTitle")
public class JobTitleController {

    private final JobTitleService jobTitleService;

    @Autowired
    public JobTitleController(JobTitleService jobTitleService){
        this.jobTitleService = jobTitleService;
    }


    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> create(@RequestBody JobTitleRequest request) {
        try {
            this.jobTitleService.add(request);
            return ResponseEntity.ok("JobTitle added successfully");
        } catch (Exception e) {
            log.error("EXCEPgetCompanyByCompNoTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add JobTitle. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> update(@RequestBody JobTitleRequest request, @RequestParam(value = "id") Long jobTitleId) {
        try {
            this.jobTitleService.update(request, jobTitleId);
            return ResponseEntity.ok("JobTitle updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update JobTitle. Please contact support");
        }
    }

    @RequestMapping( method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<JobTitleResponse> get(@RequestParam(value = "id" ) long jobTitleId) {
        try {
            JobTitle jobTitle = this.jobTitleService.getByNo(jobTitleId);

            JobTitleResponse jobTitleResponse = JobTitleResponse.builder()
                    .id(jobTitle.getJobTitleId())
                    .name(jobTitle.getName()).build();
            return ResponseEntity.status(HttpStatus.OK).body( jobTitleResponse);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping( value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<List<JobTitle>> get() {
        try {
            List<JobTitle> jobTitles = this.jobTitleService.getAll();

//            List<JobTitleResponse> jobTitleResponses = new ArrayList<>();
//            jobTitles.forEach(jobTitle -> {
//
//                JobTitleResponse jobTitleResponse = JobTitleResponse.builder()
//                        .id(jobTitle.getJobTitleId())
//                        .name(jobTitle.getName()).build();
//                jobTitleResponses.add(jobTitleResponse);
//            });

            return ResponseEntity.status(HttpStatus.OK).body(jobTitles);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<Map<String, Object>> getList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "sort", defaultValue = "0") Integer sortBy,
                                                       @RequestParam(value = "sortCol", defaultValue = "name") String sortCol) {
        try {
            int pageSize = 10;
            Pageable pageable = null;

            Sort sort = null;
            if (StringUtils.isNotBlank(sortCol)) {
                sort = Sort.by(sortCol);
                if (sortBy == 1) {
                    sort.descending();
                } else {
                    sort.ascending();
                }
                pageable = PageRequest.of(page, pageSize, sort);
            } else {
                pageable = PageRequest.of(page, pageSize);
            }

            long totalCount = this.jobTitleService.countAll();
            List<JobTitle> jobTitles = this.jobTitleService.getAll(pageable);

            List<JobTitleResponse> jobTitleResponses = new ArrayList<>();
            jobTitles.forEach(jobTitle -> {

                JobTitleResponse jobTitleResponse = JobTitleResponse.builder()
                        .id(jobTitle.getJobTitleId())
                        .name(jobTitle.getName()).build();
                jobTitleResponses.add(jobTitleResponse);
            });

            Map<String, Object> map = new HashMap<>();
            map.put("data", jobTitleResponses);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
