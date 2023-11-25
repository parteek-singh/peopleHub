package com.parsla.PeopleHub.service.configuration;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.entity.configuration.AdditionDocumentType;
import com.parsla.PeopleHub.repo.configuration.AdditionDocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdditionDocumentTypeService {

    private final AdditionDocumentTypeRepository additionalDocumentRepository;
    private final NumberUtil numberUtil;

    @Autowired
    public AdditionDocumentTypeService(AdditionDocumentTypeRepository additionalDocumentRepository,
                                       NumberUtil numberUtil) {
        this.additionalDocumentRepository = additionalDocumentRepository;
        this.numberUtil = numberUtil;
    }

    public AdditionDocumentType add(String name) {

        System.out.println("create  Addition Document Type");
        long count = additionalDocumentRepository.count();
        long docNo = numberUtil.generateIndexNo(ObjectType.DOCUMENT, count);

        AdditionDocumentType additionDocumentType = AdditionDocumentType.builder()
                .docNo(docNo)
                .name(name)
                .build();

        return additionalDocumentRepository.save(additionDocumentType);

    }

    public AdditionDocumentType update(String name, long docNo, long compNo){
        AdditionDocumentType documentType = getByNo(docNo, compNo);
        documentType.setName(name);
        return additionalDocumentRepository.save(documentType);
    }

    public AdditionDocumentType getByNo(long docNo, long compNo) {
        //Optional<AdditionDocumentType> project = additionalDocumentRepository.findByDocNoAndCompany_CompNo(docNo, compNo);
        Optional<AdditionDocumentType> project = additionalDocumentRepository.findByDocNo(docNo);
        if (project.isPresent()) {
            return project.get();
        }

        throw new RuntimeException("AdditionDocument Type not found");
    }


    public long countAll() {
        return additionalDocumentRepository.count();
    }

    public List<AdditionDocumentType> getAll(Pageable pageable, long compNo) {
//        Page<AdditionDocumentType> page =  this.additionalDocumentRepository.findAllByCompany_CompNo(pageable, compNo);
        Page<AdditionDocumentType> page =  this.additionalDocumentRepository.findAll(pageable);
        return page.getContent();
    }
}
