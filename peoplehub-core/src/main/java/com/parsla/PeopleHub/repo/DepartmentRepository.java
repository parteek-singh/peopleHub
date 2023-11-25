package com.parsla.PeopleHub.repo;

import com.parsla.PeopleHub.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("departmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDeptNo(long deptNo);
}