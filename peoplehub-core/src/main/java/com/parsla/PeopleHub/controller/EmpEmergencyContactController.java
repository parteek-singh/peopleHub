package com.parsla.PeopleHub.controller;

import com.parsla.PeopleHub.entity.employee.EmpEmergencyContact;
import com.parsla.PeopleHub.service.employee.EmpEmergencyContactService;
import com.parsla.PeopleHub.service.employee.EmployeeService;
import com.parsla.PeopleHub.view.request.EmpEmergencyContactRequest;
import com.parsla.PeopleHub.view.response.EmpEmergencyContactResponse;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("v1/emgCont")
public class EmpEmergencyContactController {

    private final EmpEmergencyContactService empEmergencyContactService;

    private final EmployeeService employeeService;

    public EmpEmergencyContactController(EmpEmergencyContactService empEmergencyContactService,
                                         EmployeeService employeeService) {
        this.empEmergencyContactService = empEmergencyContactService;
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody EmpEmergencyContactRequest request) {
        try {
            this.empEmergencyContactService.add(request);
            return ResponseEntity.ok("Employee added successfully");
        } catch (Exception e) {
            log.error("EXCEP EmpEmergencyContactController : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add Employee. Please contact support");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> update(@RequestBody EmpEmergencyContactRequest request, @RequestParam(value = "empNo") Long empNo, @RequestParam(value = "compNo") Long compNo) {
        try {
            this.empEmergencyContactService.update(request, empNo, compNo);
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update Employee. Please contact support");
        }
    }

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<EmpEmergencyContactResponse> get(@RequestParam(value = "contactId" ) long contactId, @RequestParam(value = "compNo") Long compNo) {
        try {
            EmpEmergencyContact emergencyContact = this.empEmergencyContactService.getByNo(contactId, compNo);

            EmpEmergencyContactResponse response = EmpEmergencyContactResponse.builder()
                    .contactId(emergencyContact.getContactId())
                    .name(emergencyContact.getName())
                    .relation(emergencyContact.getRelation())
                    .email(emergencyContact.getEmail())
                    .mobile(emergencyContact.getMobile())
                    .address(emergencyContact.getAddress())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body( response);
        } catch (Exception e){
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "sort", defaultValue = "0") Integer sortBy,
                                                       @RequestParam(value = "sortCol", defaultValue = "fName") String sortCol,
                                                       @RequestParam(value = "compNo") Long compNo) {
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

            long totalCount = this.empEmergencyContactService.countAll();
            List<EmpEmergencyContact> contacts = this.empEmergencyContactService.getAll(pageable, compNo);

            Map<String, Object> map = new HashMap<>();
            map.put("data", contacts);
            map.put("total", totalCount);
            map.put("perpage", pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
