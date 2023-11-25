package com.parsla.PeopleHub.entity.configuration;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobTitle")
public class JobTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobTitleId;

    @Column(name = "name")
    private String name;
}
