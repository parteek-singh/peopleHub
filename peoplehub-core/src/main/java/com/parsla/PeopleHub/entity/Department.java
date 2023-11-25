package com.parsla.PeopleHub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parsla.PeopleHub.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long deptId;

    @Column(name = "deptNo", nullable = false)
    private long deptNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "description", nullable = false)
    private String description;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comp_det_Id")
//    @JsonIgnore
//    private Company company;

    @OneToOne
    @JsonIgnore
    private Employee manager;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Account> accounts;
}
