package com.parsla.PeopleHub.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parsla.PeopleHub.entity.configuration.AdditionDocumentType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "additionalDocument")
public class AdditionalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long docId;

    @Column(name = "docNo", nullable = false)
    private long docNo;

    @Column(name = "label", nullable = false, length = 50)
    private String label;

    @Column(name = "path", nullable = false)
    private String path;

    @OneToOne
    @JsonIgnore
    private AdditionDocumentType additionDocumentType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_emp_id")
    @JsonIgnore
    private Employee employee;
}
