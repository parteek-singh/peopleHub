package com.parsla.PeopleHub.entity.configuration;

import com.parsla.PeopleHub.constant.enums.Operations;
import com.parsla.PeopleHub.constant.enums.SalaryComponentType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salaryComponent")
public class SalaryComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long salCompId;

    @Column(name = "type", nullable = false)
    SalaryComponentType type;

    @Column(name = "name", nullable = false, length = 30)
    String name;

    @Column(name = "active")
    boolean active;

    @Column(name = "included")
    boolean included;

    @Column(name = "complex")
    boolean complex;

    @Column(name = "value", nullable = false, length = 500)
    String value;

    @Column(name = "component")
    String component;

    @Column(name = "operation")
    Operations operation;
}
