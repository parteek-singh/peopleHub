package com.parsla.PeopleHub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parsla.PeopleHub.constant.enums.PayCycleType;
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
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long companyId;

    @Column(name = "compNo", nullable = false)
    private long compNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "payCycle")
    private PayCycleType payCycle;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Employee> handlers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Employee> employees;
}
