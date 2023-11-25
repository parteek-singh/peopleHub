package com.parsla.PeopleHub.repo.configuration;

import com.parsla.PeopleHub.entity.configuration.AdditionDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("additionDocumentTypeRepository")
public interface AdditionDocumentTypeRepository extends JpaRepository<AdditionDocumentType, Long> {
    Optional<AdditionDocumentType> findByDocNo(long docNo);
//    Optional<AdditionDocumentType> findByDocNoAndCompany_CompNo(long docNo, long compNo);

//    Page<AdditionDocumentType> findAllByCompany_CompNo(Pageable pageable, long compNo);
}
