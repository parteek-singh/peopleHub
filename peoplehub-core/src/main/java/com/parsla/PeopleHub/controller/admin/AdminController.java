package com.parsla.PeopleHub.controller.admin;

import com.parsla.PeopleHub.entity.UserInfo;
import com.parsla.PeopleHub.service.admin.AdminService;
import com.parsla.PeopleHub.service.authconfig.UserInfoService;
import com.parsla.PeopleHub.service.employee.EmployeeService;
import com.parsla.PeopleHub.view.request.EmployeeRequest;
import com.parsla.PeopleHub.view.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.parsla.PeopleHub.constant.ConstantString.GENERAL_ERROR_MESSAGE;

@Slf4j
@RestController
@RequestMapping("auth/admin")
public class AdminController {

    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final UserInfoService userInfoService;

    @Autowired
    public AdminController(AdminService adminService,
                           EmployeeService employeeService,
                           UserInfoService userInfoService){
        this.adminService = adminService;
        this.employeeService = employeeService;
        this.userInfoService = userInfoService;
    }


    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> addAdmin(@RequestBody UserInfo userInfo) {
        try{
            userInfo.setRoles("ROLE_ADMIN");
            String message = userInfoService.addUser(userInfo);
            return ResponseEntity.ok(ResponseMessage.builder().entity("").message(message).build());
        }catch (Exception e){
            log.error("Exception : " + e.getMessage());;
            return ResponseEntity.ok(ResponseMessage.builder().entity(null).message(GENERAL_ERROR_MESSAGE).build());
        }
    }

    @RequestMapping( method = RequestMethod.PUT)
    public ResponseEntity<ResponseMessage> updateAdmin(@RequestBody UserInfo userInfo, @RequestParam(value = "email") String email) {
        try{
            userInfo.setRoles("ROLE_ADMIN");
            String message = userInfoService.updateUser(email, userInfo);
            return ResponseEntity.ok(ResponseMessage.builder().entity("").message(message).build());
        }catch (Exception e){
            log.error("Exception : " + e.getMessage());;
            return ResponseEntity.ok(ResponseMessage.builder().entity(null).message(GENERAL_ERROR_MESSAGE).build());
        }
    }


    @RequestMapping(value = "/hrMan", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage> addHrMan(@RequestBody UserInfo userInfo) {

        try{
            userInfo.setRoles("ROLE_HR_MAN");
            String message = userInfoService.addUser(userInfo);
            return ResponseEntity.ok(ResponseMessage.builder().entity("").message(message).build());
        }catch (Exception e){
            log.error("Exception : " + e.getMessage());;
            return ResponseEntity.ok(ResponseMessage.builder().entity(null).message(GENERAL_ERROR_MESSAGE).build());
        }
    }

    @RequestMapping(value = "/hrMan", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage> updateHrMan(@RequestBody UserInfo userInfo, @RequestParam(value = "email") String email) {
        try{
            userInfo.setRoles("ROLE_HR_MAN");
            String message = userInfoService.updateUser(email, userInfo);
            return ResponseEntity.ok(ResponseMessage.builder().entity("").message(message).build());
        }catch (Exception e){
            log.error("Exception : " + e.getMessage());;
            return ResponseEntity.ok(ResponseMessage.builder().entity(null).message(GENERAL_ERROR_MESSAGE).build());
        }
    }

    @RequestMapping(value = "/initHrManEmp", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> createHrManEmp(@RequestBody EmployeeRequest request) {
        try {
            UserInfo user = userInfoService.getUser(request.getEmail());
            if(Objects.isNull(user)){
                log.error("Not able to find user with email : "+ request.getEmail());
                throw new UsernameNotFoundException("User not found");
            }
            this.employeeService.add(request);
            return ResponseEntity.ok("Employee added successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add Employee. Please contact support");
        }
    }


    @RequestMapping(value = "/addHrMan", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateHrManEmp(@RequestBody EmployeeRequest request) {
        try {
            UserInfo user = userInfoService.getUser(request.getEmail());
            if(Objects.isNull(user)){
                log.error("Not able to find user with email : "+ request.getEmail());
                throw new UsernameNotFoundException("User not found");
            }
            this.employeeService.add(request);
            return ResponseEntity.ok("Employee added successfully");
        } catch (Exception e) {
            log.error("EXCEPTION : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to add Employee. Please contact support");
        }
    }
}
