package com.parsla.PeopleHub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parsla.PeopleHub.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Column(name = "acctNo", nullable = false)
    private long acctNo;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "startDt", nullable = false)
    private Date startDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_acc_Ids")
    @JsonIgnore
    private Department department;

    @OneToOne
    @JsonIgnore
    private Employee manager;
}
