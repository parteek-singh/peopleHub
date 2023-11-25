package com.parsla.PeopleHub.service.payroll;

import com.parsla.PeopleHub.constant.enums.Operations;
import com.parsla.PeopleHub.constant.enums.PayCycleType;
import com.parsla.PeopleHub.constant.enums.SalaryComponentType;
import com.parsla.PeopleHub.entity.configuration.SalaryComponent;
import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.service.configuration.SalaryComponentService;
import com.parsla.PeopleHub.service.employee.EmployeeService;
import com.parsla.PeopleHub.view.response.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompensationService {

    private final EmployeeService employeeService;
    private final SalaryComponentService salaryComponentService;

    @Autowired
    public CompensationService(EmployeeService employeeService,
                               SalaryComponentService salaryComponentService) {
        this.employeeService = employeeService;
        this.salaryComponentService = salaryComponentService;
    }

    public List<KeyValue> getCompensation(long empNo) {
        Employee employee = employeeService.getByNo(empNo);
        if (Objects.isNull(employee)) {
            throw new RuntimeException("Employee not found");
        }
        PayCycleType payCycle = employee.getPayCycle() == null ? PayCycleType.MONTHLY : employee.getPayCycle();
        List<KeyValue> map = calculateCompensation(employee.getBaseSalary());
        System.out.println();


        return map;

    }

    public List<KeyValue> calculateCompensation(double baseSalary) {
        Map<String, Map<String, Double>> map = new HashMap<>();

        List<SalaryComponent> salaryComponents = salaryComponentService.getAll();

        //double baseSalary = Double.parseDouble(baseComponent.get().getValue());
        double total = 0;
        double allowance = 0;
        double earning = baseSalary;
        double deduction = 0;
        List<KeyValue> earningList = new ArrayList<>();
        List<KeyValue> allowanceList = new ArrayList<>();
        List<KeyValue> deductionList = new ArrayList<>();
        List<KeyValue> totalList = new ArrayList<>();

        Map<String, Double> earningMap = new HashMap<>();
        Map<String, Double> allowanceMap = new HashMap<>();
        Map<String, Double> deductionMap = new HashMap<>();
        Map<String, Double> totalMap = new HashMap<>();
        earningMap.put("Base Salary", baseSalary);
        earningList.add(KeyValue.builder().key("Base Salary").value(baseSalary).build());
        for (SalaryComponent component : salaryComponents) {

            String name = component.getName();

            // Added more in base salary
            if (component.getType() == SalaryComponentType.Earning) {
                double amount = getAmount(component, baseSalary);

                earningList.add(KeyValue.builder().key(component.getName()).value(amount).build());

//                earningMap.put(component.getName(), amount);
                earning += amount;
            }
            // consider as a part of base salary
            if (component.getType() == SalaryComponentType.Allowance) {
                double amount = getAmount(component, baseSalary);
                allowanceList.add(KeyValue.builder().key(component.getName()).value(amount).build());
                //allowanceMap.put(component.getName(), amount);
                allowance += amount;
            }

            if (component.getType() == SalaryComponentType.Deduction) {
                double amount = getAmount(component, baseSalary);
                deductionList.add(KeyValue.builder().key(component.getName()).value(amount).build());
                //deductionMap.put(component.getName(), amount);
                deduction += amount;
            }

        }
//        totalMap.put("Earning", earning);
//        totalMap.put("Allowance", allowance);
//        totalMap.put("Deduction", deduction);
//        totalMap.put("NetPay", (earning - deduction));
        totalList.add(KeyValue.builder().key("Earning").value(earning).build());
        totalList.add(KeyValue.builder().key("Allowance").value(allowance).build());
        totalList.add(KeyValue.builder().key("Deduction").value(deduction).build());
        totalList.add(KeyValue.builder().key("NetPay").value((earning - deduction)).build());

        map.put("Earning", earningMap);
        map.put("Allowance", allowanceMap);
        map.put("Deduction", deductionMap);
        map.put("Total", totalMap);


        List<KeyValue> finalList = new ArrayList<>();
        finalList.add(KeyValue.builder().key("Earning").value(earningList).build());
        finalList.add(KeyValue.builder().key("Allowance").value(allowanceList).build());
        finalList.add(KeyValue.builder().key("Deduction").value(deductionList).build());
        finalList.add(KeyValue.builder().key("Total").value(totalList).build());
        return finalList;
    }

    private double getAmount(SalaryComponent component, double baseSalary) {
        double amount = 0;
        double value = Double.parseDouble(component.getValue());
        if (component.isComplex()) {
            Operations operations = component.getOperation();
            if (operations == Operations.PERCENT) {
                amount = baseSalary * (value / 100);
            } else if (operations == Operations.ADD) {
                amount = baseSalary + value;
            } else if (operations == Operations.MINUS) {
                amount = baseSalary - value;
            }
        } else {
            amount = value;
        }
        return amount;
    }
}
