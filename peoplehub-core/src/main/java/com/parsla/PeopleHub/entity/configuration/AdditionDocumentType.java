package com.parsla.PeopleHub.entity.configuration;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "additionDocumentType")
public class AdditionDocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long additionDocId;

    @Column(name = "docNo", nullable = false)
    private long docNo;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

}
