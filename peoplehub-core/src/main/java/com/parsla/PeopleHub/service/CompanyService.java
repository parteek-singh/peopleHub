package com.parsla.PeopleHub.service;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.entity.Company;
import com.parsla.PeopleHub.repo.CompanyRepository;
import com.parsla.PeopleHub.view.request.CompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final NumberUtil numberUtil;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,
                          NumberUtil numberUtil) {
        this.companyRepository = companyRepository;
        this.numberUtil = numberUtil;
    }

    public Company add(CompanyRequest companyRequest) {

        System.out.println("create  Company");
        long count = companyRepository.count();
        long compNo = numberUtil.generateIndexNo(ObjectType.COMPANY, count);

        Company company = Company.builder()
                .name(companyRequest.getName())
                .compNo(compNo)
                .location(companyRequest.getLocation())
                .build();

        return  companyRepository.save(company);
    }

    public Company update(CompanyRequest companyRequest, long compNo){
        Company company= getByNo(compNo);
        company.setName(companyRequest.getName());
        company.setLocation(companyRequest.getLocation());
        return companyRepository.save(company);
    }

    public Company getByNo(long companyId) {
        Optional<Company> company = companyRepository.findByCompNo(companyId);
        if (company.isPresent()) {
            return company.get();
        }

        throw new RuntimeException("Company not found");
    }


    public long countAll() {
        return companyRepository.count();
    }

    public List<Company> getAll(Pageable pageable) {
        Page<Company> page =  this.companyRepository.findAll(pageable);
        return page.getContent();
    }
}
