package com.parsla.PeopleHub.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parsla.PeopleHub.constant.enums.Relationship;
import com.parsla.PeopleHub.entity.Company;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empEmergencyContact")
public class EmpEmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contactId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "relation", nullable = false)
    private Relationship relation;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "MOB", nullable = false, length = 15, unique = true)
    private String mobile;

    @Column(name = "ADDRESS", nullable = false, length = 256)
    private String address;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_det_Id", nullable=false)
    @JsonIgnore
    private Company company;

    @OneToOne
    @JsonIgnore
    private Employee employee;
}
