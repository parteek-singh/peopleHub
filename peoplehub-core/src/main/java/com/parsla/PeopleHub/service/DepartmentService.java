package com.parsla.PeopleHub.service;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.entity.Department;
import com.parsla.PeopleHub.repo.DepartmentRepository;
import com.parsla.PeopleHub.view.request.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final NumberUtil numberUtil;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository,
                             NumberUtil numberUtil) {
        this.departmentRepository = departmentRepository;
        this.numberUtil = numberUtil;
    }

    public Department add( DepartmentRequest departmentView) {

        System.out.println("create  department");
        long count = departmentRepository.count();
        long deptNo = numberUtil.generateIndexNo(ObjectType.DOCUMENT, count);

        Department department = Department.builder()
                .deptNo(deptNo)
                .name(departmentView.getName())
                .description(departmentView.getDesc())
                .location(departmentView.getLocation())
                .build();

        return departmentRepository.save(department);

    }

    public Department update(DepartmentRequest departmentView, long deptNo){
        Department department = getByNo(deptNo);
        department.setName(departmentView.getName());
        department.setDescription(departmentView.getDesc());
        department.setLocation(departmentView.getLocation());
        return departmentRepository.save(department);
    }

    public Department getByNo(long deptNo) {
        Optional<Department> department = departmentRepository.findByDeptNo(deptNo);
        if (department.isPresent()) {
            return department.get();
        }

        throw new RuntimeException("Department not found");
    }


    public boolean updateManager(long deptNo) {
        return true;
    }

    public long countAll() {
        return departmentRepository.count();
    }

    public List<Department> getAll(Pageable pageable) {
        Page<Department> page =  this.departmentRepository.findAll(pageable);
        return page.getContent();
    }
}
