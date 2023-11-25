package com.parsla.PeopleHub.entity.payroll;

import com.parsla.PeopleHub.constant.enums.PayCycleType;
import com.parsla.PeopleHub.constant.enums.SalaryComponentType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payslipGenerationLog")
public class PayslipGenerationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payGenId;

    @Column(name = "type", nullable = false)
    PayCycleType type;


}


//    @Column(name = "year", nullable = false)
//    private int year;
//
//    @Column(name = "generationDate", nullable = false)
//    private Date generationDate;
//
////    @Lob
//    @Column(name = "payslipData", nullable = false)
//    private String payslipData;

//    @OneToOne
//    @JsonIgnore
//    private Employee employee;