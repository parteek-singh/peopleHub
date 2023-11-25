package com.parsla.PeopleHub.controller.configuration;


import com.parsla.PeopleHub.entity.configuration.SalaryComponent;
import com.parsla.PeopleHub.service.configuration.SalaryComponentService;
import com.parsla.PeopleHub.view.request.SalaryComponentRequest;
import com.parsla.PeopleHub.view.response.SalaryComponentResponse;
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
@RequestMapping("v1/hrman/salComp")
public class SalaryComponentController {

    private final SalaryComponentService salaryComponentService;

    @Autowired
    public SalaryComponentController(SalaryComponentService salaryComponentService){
        this.salaryComponentService = salaryComponentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> create(@RequestBody SalaryComponentRequest request) {
        try {
            this.salaryComponentService.add(request);
            return ResponseEntity.ok("JobTitle added successfully");
        } catch (Exception e) {
            log.error("EXCEPgetCompanyByCompNoTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add JobTitle. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> update(@RequestBody SalaryComponentRequest request, @RequestParam(value = "id") Long jobTitleId) {
        try {
            this.salaryComponentService.update(request, jobTitleId);
            return ResponseEntity.ok("JobTitle updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update JobTitle. Please contact support");
        }
    }

    @RequestMapping( method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<SalaryComponentResponse> get(@RequestParam(value = "id" ) long salCompId) {
        try {
            SalaryComponent component = this.salaryComponentService.getByNo(salCompId);

            SalaryComponentResponse jobTitleResponse = SalaryComponentResponse.builder()
                    .id(component.getSalCompId())
                    .name(component.getName())
                    .type(component.getType())
                    .included(component.isIncluded())
                    .active(component.isActive())
                    .complex(component.isComplex())
                    .component(component.getComponent())
                    .operation(component.getOperation())
                    .value(component.getValue()).build();
            return ResponseEntity.status(HttpStatus.OK).body( jobTitleResponse);
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

            long totalCount = this.salaryComponentService.countAll();
            List<SalaryComponent> list = this.salaryComponentService.getAll(pageable);

            List<SalaryComponentResponse>  componentResponses = new ArrayList<>();
            list.forEach(component -> {

                SalaryComponentResponse jobTitleResponse = SalaryComponentResponse.builder()
                        .id(component.getSalCompId())
                        .name(component.getName())
                        .type(component.getType())
                        .active(component.isActive())
                        .complex(component.isComplex())
                        .included(component.isIncluded())
                        .component(component.getComponent())
                        .operation(component.getOperation())
                        .value(component.getValue()).build();
                componentResponses.add(jobTitleResponse);
            });

            Map<String, Object> map = new HashMap<>();
            map.put("data", componentResponses);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
