package com.parsla.PeopleHub.repo.employee;

import com.parsla.PeopleHub.entity.employee.AdditionalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("additionalDocumentRepository")
public interface AdditionalDocumentRepository extends JpaRepository<AdditionalDocument, Long> {
    Optional<AdditionalDocument> findByDocNo(long docNo);
//    Optional<AdditionalDocument> findByDocNoAndCompany_CompNo(long docNo, long compNo);
//
//    Page<AdditionalDocument> findAllByCompany_CompNo(Pageable pageable, long compNo);
}
