package com.parsla.PeopleHub.repo;

import com.parsla.PeopleHub.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
