package com.parsla.PeopleHub.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parsla.PeopleHub.constant.enums.GenderType;
import com.parsla.PeopleHub.constant.enums.PayCycleType;
import com.parsla.PeopleHub.entity.Account;
import com.parsla.PeopleHub.entity.Company;
import com.parsla.PeopleHub.entity.Department;
import com.parsla.PeopleHub.entity.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    @Column(name = "empNo", nullable = false)
    private long empNo;

    @Column(name = "fName", nullable = false, length = 50)
    private String fName;

    @Column(name = "mName", nullable = false, length = 50)
    private String mName;

    @Column(name = "lName", nullable = false, length = 50)
    private String lName;

    @Column(name = "gender", nullable = false, length = 1)
    private GenderType gender;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "hireDt", nullable = false)
    private Date hireDt;

    @Column(name = "lastDt")
    private Date lastDt;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;


    @Column(name = "mobile", nullable = false, length = 15, unique = true)
    private String mobile;

    @Column(name = "address", nullable = false, length = 256)
    private String address;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "jobTitle", nullable = false, length = 100)
    private String jobTitle;

    @Column(name = "baseSalary", nullable = false)
    private double baseSalary;

    @Column(name = "payCycle")
    private PayCycleType payCycle;

    @OneToOne
    @JsonIgnore
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acct_emp_Id")
    @JsonIgnore
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_emp_Id")
    @JsonIgnore
    private Department department;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<AdditionalDocument> documents;

}
