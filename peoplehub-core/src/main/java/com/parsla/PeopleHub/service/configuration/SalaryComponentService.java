package com.parsla.PeopleHub.service.configuration;

import com.parsla.PeopleHub.entity.configuration.SalaryComponent;
import com.parsla.PeopleHub.repo.configuration.SalaryComponentRepository;
import com.parsla.PeopleHub.view.request.SalaryComponentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryComponentService {

    private final SalaryComponentRepository repository;

    @Autowired
    public SalaryComponentService(SalaryComponentRepository repository){
        this.repository = repository;
    }

    public SalaryComponent add(SalaryComponentRequest request) {

        System.out.println("create Salary Component");
        SalaryComponent salaryComponent = SalaryComponent.builder()
                .value(request.getValue())
                .name(request.getName())
                .active(request.isActive())
                .included(request.isIncluded())
                .type(request.getType())
                .complex(request.isComplex())
                .build();
        return repository.save(salaryComponent);

    }

    public SalaryComponent update(SalaryComponentRequest request, long salCompId){

        SalaryComponent component = getByNo(salCompId);
        component.setName(request.getName());
        component.setActive(request.isActive());
        component.setIncluded(request.isIncluded());
        component.setType(request.getType());
        component.setComplex(request.isComplex());

        if(request.isComplex()){
            component.setValue(request.getValue());
            component.setOperation(request.getOperation());
            component.setComponent(request.getComponent());
        } else{
            component.setValue(request.getValue());
        }
        return repository.save(component);
    }

    public SalaryComponent getByNo(long salCompId) {
        Optional<SalaryComponent> component = repository.findBySalCompId(salCompId);
        if (component.isPresent()) {
            return component.get();
        }

        throw new RuntimeException("Salary Component not found");
    }

    public List<SalaryComponent> getAllActive(){
        return repository.findAllByActive(true);
    }

    public List<SalaryComponent> getAll(){
        return repository.findAll();
    }

    public long countAll() {
        return repository.count();
    }

    public List<SalaryComponent> getAll(Pageable pageable) {
        Page<SalaryComponent> page =  this.repository.findAll(pageable);
        return page.getContent();
    }
}
