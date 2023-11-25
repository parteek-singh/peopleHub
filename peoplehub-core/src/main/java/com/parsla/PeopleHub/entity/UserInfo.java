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
@Table(name = "userInfo")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long userId;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isNew",columnDefinition = "boolean default true")
    private boolean isNew;

    @Column(name = "RESET_TOKEN")
    private String resetToken;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "USER_ROLES", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
//            @JoinColumn(name = "ROLE_ID")})
//    private Set<Roles> roles;

    private String roles;

    @OneToOne
    @JsonIgnore
    private Employee employee;
}
