package com.parsla.PeopleHub.service;

import com.parsla.PeopleHub.constant.enums.RoleEnum;
import com.parsla.PeopleHub.entity.Roles;
import com.parsla.PeopleHub.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Set<Roles> getRoles(List<RoleEnum> userRoles){
        Set<Roles> roles = new HashSet<>();
        for (RoleEnum roleName : userRoles) {
            Roles role = this.roleRepository.findByName(roleName.name());
            roles.add(role);
        }
        return roles;
    }

    public Roles findRoleByName(String name) {
        return roleRepository.findByName(name);

    }
}
