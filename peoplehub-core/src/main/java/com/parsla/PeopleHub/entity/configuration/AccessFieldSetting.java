package com.parsla.PeopleHub.entity.configuration;

import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accessFieldSetting")
public class AccessFieldSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accessFieldSettingId;

    // This will tell this setting for which type (Employee or account). Same table is used for holding different config for different tables
    @Column(name = "type")
    private AFieldSettingType type;

    @Column(name = "accFieldName1")
    private String accFieldName1;

    @Column(name = "accFieldName2")
    private String accFieldName2;

    @Column(name = "accFieldName3")
    private String accFieldName3;

    @Column(name = "accFieldName4")
    private String accFieldName4;

    @Column(name = "accFieldName5")
    private String accFieldName5;

    @Column(name = "accFieldName6")
    private String accFieldName6;

    @Column(name = "accFieldName7")
    private String accFieldName7;

    @Column(name = "accFieldName8")
    private String accFieldName8;

    @Column(name = "accFieldName9")
    private String accFieldName9;

    @Column(name = "accFieldName10")
    private String accFieldName10;


    @Column(name = "accFieldNoName1")
    private String accFieldNoName1;

    @Column(name = "accFieldNoName2")
    private String accFieldNoName2;

    @Column(name = "accFieldNoName3")
    private String accFieldNoName3;

    @Column(name = "accFieldNoName4")
    private String accFieldNoName4;

    @Column(name = "accFieldNoName5")
    private String accFieldNoName5;

    @Column(name = "accFieldNoName6")
    private String accFieldNoName6;

    @Column(name = "accFieldNoName7")
    private String accFieldNoName7;

    @Column(name = "accFieldNoName8")
    private String accFieldNoName8;

    @Column(name = "accFieldNoName9")
    private String accFieldNoName9;

    @Column(name = "accFieldNoName10")
    private String accFieldNoName10;


    @Column(name = "accFieldDateName1")
    private String accFieldDateName1;

    @Column(name = "accFieldDateName2")
    private String accFieldDateName2;

    @Column(name = "accFieldDateName3")
    private String accFieldDateName3;

    @Column(name = "accFieldDateName4")
    private String accFieldDateName4;

    @Column(name = "accFieldDateName5")
    private String accFieldDateName5;

    @Column(name = "accFieldDateName6")
    private String accFieldDateName6;

    @Column(name = "accFieldDateName7")
    private String accFieldDateName7;

    @Column(name = "accFieldDateName8")
    private String accFieldDateName8;

    @Column(name = "accFieldDateName9")
    private String accFieldDateName9;

    @Column(name = "accFieldDateName10")
    private String accFieldDateName10;



}
