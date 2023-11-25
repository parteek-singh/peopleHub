package com.parsla.PeopleHub.controller;

import com.parsla.PeopleHub.entity.Department;
import com.parsla.PeopleHub.service.DepartmentService;
import com.parsla.PeopleHub.view.request.DepartmentRequest;
import com.parsla.PeopleHub.view.response.DepartmentListView;
import com.parsla.PeopleHub.view.response.DepartmentResponse;
import com.parsla.PeopleHub.view.response.EmpManagerResponse;
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
@RequestMapping("v1/depart")@PreAuthorize("hasAuthority('ROLE_HR_MAN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController( DepartmentService departmentService){
        this.departmentService = departmentService;
    }


    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> create(@RequestBody DepartmentRequest request) {
        try {
            this.departmentService.add(request);
            return ResponseEntity.ok("Department added successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add Department. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> update(@RequestBody DepartmentRequest request, @RequestParam(value = "deptNo") Long deptNo) {
        try {
            this.departmentService.update(request, deptNo);
            return ResponseEntity.ok("Department updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update Department. Please contact support");
        }
    }

    @RequestMapping( method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<DepartmentResponse> get(@RequestParam(value = "deptNo" ) long deptNo) {
        try {
            Department department = this.departmentService.getByNo(deptNo);
            DepartmentResponse departmentResponse = DepartmentResponse.builder()
                    .name(department.getName())
                    .desc(department.getDescription())
                    .location(department.getLocation()).build();

            return ResponseEntity.status(HttpStatus.OK).body( departmentResponse);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<Map<String, Object>> getList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(value = "sort", defaultValue = "0") Integer sortBy,
                                                              @RequestParam(value = "sortCol", defaultValue = "name") String sortCol ) {
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

            long totalCount = this.departmentService.countAll();
            List<Department> departments = this.departmentService.getAll(pageable);

            List<DepartmentListView> departmentList = new ArrayList<>();
            departments.forEach(department -> {

                DepartmentListView departmentListView = DepartmentListView.builder()
                        .id(department.getDeptNo())
                        .name(department.getName())
                        .location(department.getLocation())
                        .description(department.getDescription())
                        .managerResponse(null)
                        .build();

                departmentList.add(departmentListView);
            });

            Map<String, Object> map = new HashMap<>();
            map.put("data", departmentList);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
