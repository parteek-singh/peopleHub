package com.parsla.PeopleHub.controller;

import com.parsla.PeopleHub.entity.UserInfo;
import com.parsla.PeopleHub.service.authconfig.JwtService;
import com.parsla.PeopleHub.service.authconfig.UserInfoService;
import com.parsla.PeopleHub.view.request.AuthRequest;
import com.parsla.PeopleHub.view.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> welcome() {
        return ResponseEntity.ok(ResponseMessage.builder().entity("").message("Welcome this endpoint is not secure").build());
    }


    @RequestMapping(value = "/addManager", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage> addManager(@RequestBody UserInfo userInfo) {
        userInfo.setRoles("ROLE_MANAGER");
        String message = service.addUser(userInfo);
        return ResponseEntity.ok(ResponseMessage.builder().entity("").message(message).build());
    }


    @RequestMapping(value = "/addHr", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN','ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage> addHr(@RequestBody UserInfo userInfo) {
        userInfo.setRoles("ROLE_HR");
        String message = service.addUser(userInfo);
        return ResponseEntity.ok(ResponseMessage.builder().entity("").message(message).build());
    }

    @RequestMapping(value = "/addEmp", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HR_MAN', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage> addEmp(@RequestBody UserInfo userInfo) {
        userInfo.setRoles("ROLE_HR_MAN");
        String message = service.addUser(userInfo);
        return ResponseEntity.ok(ResponseMessage.builder().entity("").message(message).build());
    }

    @RequestMapping(value = "/user/userProfile", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_EMP')")
    public ResponseEntity<ResponseMessage> userProfile() {
        return ResponseEntity.ok(ResponseMessage.builder().entity("").message("Welcome to User Profile").build());
    }

    @RequestMapping(value = "/admin/adminProfile", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @RequestMapping(value = "/generateToken", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

                String token = jwtService.generateToken(authRequest.getUsername(), String.join(",", roles));
                return ResponseEntity.ok(ResponseMessage.builder().entity(token).build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder().entity("").message("User not found. Please try again").build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder().entity("").message("User not found. Please try again").build());
        }
    }
}
