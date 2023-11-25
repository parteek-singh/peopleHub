package com.parsla.PeopleHub.controller;

import com.parsla.PeopleHub.entity.Company;
import com.parsla.PeopleHub.service.CompanyService;
import com.parsla.PeopleHub.view.request.CompanyRequest;
import com.parsla.PeopleHub.view.response.CompanyResponse;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("v1/admin/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> create(@RequestBody CompanyRequest companyRequest) {
        try {
            this.companyService.add(companyRequest);
            return ResponseEntity.ok("Company added successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add Company. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_HR')")
    public ResponseEntity<String> update(@RequestBody CompanyRequest companyRequest, @RequestParam(value = "compId") Long compId) {
        try {
            this.companyService.update(companyRequest, compId);
            return ResponseEntity.ok("Company updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update Company. Please contact support");
        }
    }

    @RequestMapping(value = "/{compId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_HR')")
    public ResponseEntity<CompanyResponse> get(@PathVariable(value = "compId") long compId) {
        try {
            Company company = this.companyService.getByNo(compId);
            CompanyResponse companyResponse = CompanyResponse.builder()
                    .compNo(company.getCompNo())
                    .name(company.getName())
                    .location(company.getLocation()).build();

            return ResponseEntity.status(HttpStatus.OK).body(companyResponse);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

            long totalCount = this.companyService.countAll();
            List<Company> companies = this.companyService.getAll(pageable);

            Map<String, Object> map = new HashMap<>();
            map.put("data", companies);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
