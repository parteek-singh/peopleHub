package com.parsla.PeopleHub.controller.admin;

import com.parsla.PeopleHub.service.admin.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/manager")
public class ManagerController {

    private final ManagerService  managerService;

    @Autowired
    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }
}
