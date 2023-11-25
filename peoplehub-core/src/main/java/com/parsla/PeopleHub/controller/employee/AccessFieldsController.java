package com.parsla.PeopleHub.controller.employee;

import com.parsla.PeopleHub.service.employee.AccessFieldsService;
import com.parsla.PeopleHub.view.request.AccessFieldMapView;
import com.parsla.PeopleHub.view.request.AccessFieldUpdateView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("v1/emp/afield")
public class AccessFieldsController {

    private final AccessFieldsService accessFieldsService;


    @Autowired
    public AccessFieldsController(AccessFieldsService accessFieldsService) {
        this.accessFieldsService = accessFieldsService;

    }


    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<AccessFieldMapView> get(@RequestParam(value = "empNo") long empNo) {
        try {

            AccessFieldMapView accessFieldMapView = accessFieldsService.getAccessFields(empNo);
            return ResponseEntity.status(HttpStatus.OK).body(accessFieldMapView);
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN')")
    public ResponseEntity<String> saveOrUpdate(@RequestBody AccessFieldUpdateView request, @RequestParam(value = "empNo") Long empNo) {
        try {
            this.accessFieldsService.saveAccessFields(request, empNo);
            return ResponseEntity.ok("AccessFields updated successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to update AccessFields. Please contact support");
        }
    }
}
