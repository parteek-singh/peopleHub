package com.parsla.PeopleHub.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accessFields")
public class AccessFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accessFieldId;

    // This will tell this setting for which type (Employee or account). Same table is used for holding different config for different tables
    @Column(name = "type")
    private AFieldSettingType type;

    @Column(name = "accessField1")
    private String accessField1;

    @Column(name = "accessField2")
    private String accessField2;

    @Column(name = "accessField3")
    private String accessField3;

    @Column(name = "accessField4")
    private String accessField4;

    @Column(name = "accessField5")
    private String accessField5;

    @Column(name = "accessField6")
    private String accessField6;

    @Column(name = "accessField7")
    private String accessField7;

    @Column(name = "accessField8")
    private String accessField8;

    @Column(name = "accessField9")
    private String accessField9;

    @Column(name = "accessField10")
    private String accessField10;


    @Column(name = "accessFieldNo1")
    private int accessFieldNo1;

    @Column(name = "accessFieldNo2")
    private int accessFieldNo2;

    @Column(name = "accessFieldNo3")
    private int accessFieldNo3;

    @Column(name = "accessFieldNo4")
    private int accessFieldNo4;

    @Column(name = "accessFieldNo5")
    private int accessFieldNo5;

    @Column(name = "accessFieldNo6")
    private int accessFieldNo6;

    @Column(name = "accessFieldNo7")
    private int accessFieldNo7;

    @Column(name = "accessFieldNo8")
    private int accessFieldNo8;

    @Column(name = "accessFieldNo9")
    private int accessFieldNo9;

    @Column(name = "accessFieldNo10")
    private int accessFieldNo10;


    @Column(name = "accessFieldDate1")
    private Date accessFieldDate1;

    @Column(name = "accessFieldDate2")
    private Date accessFieldDate2;

    @Column(name = "accessFieldDate3")
    private Date accessFieldDate3;

    @Column(name = "accessFieldDate4")
    private Date accessFieldDate4;

    @Column(name = "accessFieldDate5")
    private Date accessFieldDate5;

    @Column(name = "accessFieldDate6")
    private Date accessFieldDate6;

    @Column(name = "accessFieldDate7")
    private Date accessFieldDate7;

    @Column(name = "accessFieldDate8")
    private Date accessFieldDate8;

    @Column(name = "accessFieldDate9")
    private Date accessFieldDate9;

    @Column(name = "accessFieldDate10")
    private Date accessFieldDate10;

    @OneToOne
    @JsonIgnore
    private Employee employee;
}
