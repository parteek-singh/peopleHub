package com.parsla.PeopleHub.controller.admin;

import com.parsla.PeopleHub.service.admin.HrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/hr")
public class HrController {
    private final HrService hrService;

    @Autowired
    public HrController(HrService hrService){
        this.hrService = hrService;
    }
}
