package com.parsla.PeopleHub.service.employee;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.entity.employee.AdditionalDocument;
import com.parsla.PeopleHub.repo.employee.AdditionalDocumentRepository;
import com.parsla.PeopleHub.view.request.AdditionalDocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdditionalDocumentService {

    private final AdditionalDocumentRepository additionalDocumentRepository;
    private final NumberUtil numberUtil;
    @Autowired
    public AdditionalDocumentService(AdditionalDocumentRepository additionalDocumentRepository,
                                     NumberUtil numberUtil) {
        this.additionalDocumentRepository = additionalDocumentRepository;
        this.numberUtil = numberUtil;
    }

    public AdditionalDocument add(AdditionalDocumentRequest request) {

        System.out.println("create  Addition Document");
        long count = additionalDocumentRepository.count();
        long docNo = numberUtil.generateIndexNo(ObjectType.DOCUMENT, count);

        AdditionalDocument additionalDocument = AdditionalDocument.builder()
                .docNo(docNo)
                .label(request.getLabel())
                .path(request.getPath())
                .build();

        return additionalDocumentRepository.save(additionalDocument);

    }

    public AdditionalDocument update(AdditionalDocumentRequest request, long docNo, long compNo){
        AdditionalDocument document = getByNo(docNo, compNo);
        document.setLabel(request.getLabel());
        document.setPath(request.getPath());
        return additionalDocumentRepository.save(document);
    }

    public AdditionalDocument getByNo(long docNo, long compNo) {
//        Optional<AdditionalDocument> document = additionalDocumentRepository.findByDocNoAndCompany_CompNo(docNo, compNo);
        Optional<AdditionalDocument> document = additionalDocumentRepository.findByDocNo(docNo);
        if (document.isPresent()) {
            return document.get();
        }

        throw new RuntimeException("AdditionDocument not found");
    }

    public long countAll() {
        return additionalDocumentRepository.count();
    }

    public List<AdditionalDocument> getAll(Pageable pageable, long compNo) {
//        Page<AdditionalDocument> page =  this.additionalDocumentRepository.findAllByCompany_CompNo(pageable, compNo);
        Page<AdditionalDocument> page =  this.additionalDocumentRepository.findAll(pageable);
        return page.getContent();
    }
}
