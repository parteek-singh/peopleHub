package com.parsla.PeopleHub.service.employee;

import com.parsla.PeopleHub.common.DateUtil;
import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.AFieldSettingType;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.entity.configuration.AccessFieldSetting;
import com.parsla.PeopleHub.entity.employee.AccessFields;
import com.parsla.PeopleHub.entity.employee.Employee;
import com.parsla.PeopleHub.repo.employee.AccessFieldsRepository;
import com.parsla.PeopleHub.service.configuration.AccessFieldSettingService;
import com.parsla.PeopleHub.view.request.AccessFieldMapView;
import com.parsla.PeopleHub.view.request.AccessFieldUpdateView;
import com.parsla.PeopleHub.view.request.AccessFieldView;
import com.parsla.PeopleHub.view.request.AccessFieldsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AccessFieldsService {

    private final AccessFieldsRepository accessFieldsRepository;
    private final AccessFieldSettingService accessFieldSettingService;
    private final EmployeeService employeeService;

    private final NumberUtil numberUtil;
    private final DateUtil dateUtil;

    @Autowired
    public AccessFieldsService(AccessFieldsRepository accessFieldsRepository,
                               AccessFieldSettingService accessFieldSettingService,
                               EmployeeService employeeService,
                               DateUtil dateUtil,
                               NumberUtil numberUtil) {
        this.accessFieldsRepository = accessFieldsRepository;
        this.accessFieldSettingService = accessFieldSettingService;
        this.employeeService = employeeService;
        this.numberUtil = numberUtil;
        this.dateUtil = dateUtil;
    }

    public AccessFields add(AccessFieldsRequest request) {

        System.out.println("create  AccessFields");
        long count = accessFieldsRepository.count();
        long accessFieldSettingId = numberUtil.generateIndexNo(ObjectType.ACCESS_FIELD, count);

        AccessFields accessFields = AccessFields.builder()
                .accessFieldId(accessFieldSettingId)
                .accessField1(request.getAccessField1())
                .accessField2(request.getAccessField2())
                .accessField3(request.getAccessField3())
                .accessField4(request.getAccessField4())
                .accessField5(request.getAccessField5())
                .accessField6(request.getAccessField6())
                .accessField7(request.getAccessField7())
                .accessField8(request.getAccessField8())
                .accessField9(request.getAccessField9())
                .accessField10(request.getAccessField10())


                .accessFieldNo1(request.getAccessFieldNo1())
                .accessFieldNo2(request.getAccessFieldNo2())
                .accessFieldNo3(request.getAccessFieldNo3())
                .accessFieldNo4(request.getAccessFieldNo4())
                .accessFieldNo5(request.getAccessFieldNo5())
                .accessFieldNo6(request.getAccessFieldNo6())
                .accessFieldNo7(request.getAccessFieldNo7())
                .accessFieldNo8(request.getAccessFieldNo8())
                .accessFieldNo9(request.getAccessFieldNo9())
                .accessFieldNo10(request.getAccessFieldNo10())

                .accessFieldDate1(request.getAccessFieldDate1())
                .accessFieldDate2(request.getAccessFieldDate2())
                .accessFieldDate3(request.getAccessFieldDate3())
                .accessFieldDate4(request.getAccessFieldDate4())
                .accessFieldDate5(request.getAccessFieldDate5())
                .accessFieldDate6(request.getAccessFieldDate6())
                .accessFieldDate7(request.getAccessFieldDate7())
                .accessFieldDate8(request.getAccessFieldDate8())
                .accessFieldDate9(request.getAccessFieldDate9())
                .accessFieldDate10(request.getAccessFieldDate10())
                .build();

        return accessFieldsRepository.save(accessFields);

    }

    public AccessFields update(AccessFieldsRequest request, long accessFieldSettingId) {
        AccessFields accessFields = getByNo(accessFieldSettingId);
        accessFields.setAccessField1(request.getAccessField1());
        accessFields.setAccessField2(request.getAccessField2());
        accessFields.setAccessField3(request.getAccessField3());
        accessFields.setAccessField4(request.getAccessField4());
        accessFields.setAccessField5(request.getAccessField5());
        accessFields.setAccessField6(request.getAccessField6());
        accessFields.setAccessField7(request.getAccessField7());
        accessFields.setAccessField8(request.getAccessField8());
        accessFields.setAccessField9(request.getAccessField9());
        accessFields.setAccessField10(request.getAccessField10());

        accessFields.setAccessFieldNo1(request.getAccessFieldNo1());
        accessFields.setAccessFieldNo2(request.getAccessFieldNo2());
        accessFields.setAccessFieldNo3(request.getAccessFieldNo3());
        accessFields.setAccessFieldNo4(request.getAccessFieldNo4());
        accessFields.setAccessFieldNo5(request.getAccessFieldNo5());
        accessFields.setAccessFieldNo6(request.getAccessFieldNo6());
        accessFields.setAccessFieldNo7(request.getAccessFieldNo7());
        accessFields.setAccessFieldNo8(request.getAccessFieldNo8());
        accessFields.setAccessFieldNo9(request.getAccessFieldNo9());
        accessFields.setAccessFieldNo10(request.getAccessFieldNo10());


        accessFields.setAccessFieldDate1(request.getAccessFieldDate1());
        accessFields.setAccessFieldDate2(request.getAccessFieldDate2());
        accessFields.setAccessFieldDate3(request.getAccessFieldDate3());
        accessFields.setAccessFieldDate4(request.getAccessFieldDate4());
        accessFields.setAccessFieldDate5(request.getAccessFieldDate5());
        accessFields.setAccessFieldDate6(request.getAccessFieldDate6());
        accessFields.setAccessFieldDate7(request.getAccessFieldDate7());
        accessFields.setAccessFieldDate8(request.getAccessFieldDate8());
        accessFields.setAccessFieldDate9(request.getAccessFieldDate9());
        accessFields.setAccessFieldDate10(request.getAccessFieldDate10());
        return accessFieldsRepository.save(accessFields);
    }

    public AccessFields getByNo(long accessFieldId) {
        Optional<AccessFields> accessFields = accessFieldsRepository.findByAccessFieldId(accessFieldId);
        if (accessFields.isPresent()) {
            return accessFields.get();
        }

        throw new RuntimeException("AccessFields Type not found");
    }


    public long countAll() {
        return accessFieldsRepository.count();
    }

    public List<AccessFields> getAll(Pageable pageable, long compNo) {
        Page<AccessFields> page = this.accessFieldsRepository.findAll(pageable);
        return page.getContent();
    }

    public AccessFields getByEmpNo(long empNo) {
        Optional<AccessFields> accessFields = accessFieldsRepository.findByEmployee_EmpNo(empNo);
        return accessFields.orElse(null);
    }

    public AccessFieldMapView getAccessFields(long empNo) {

        Map<String, AccessFieldView> map = new HashMap<>();
        AccessFieldSetting accessFieldSetting = accessFieldSettingService.getByType(AFieldSettingType.EMP);
        AccessFields accessFields = getByEmpNo(empNo);

        updateMap("name1", accessFieldSetting.getAccFieldName1(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField1(), map);
        updateMap("name2", accessFieldSetting.getAccFieldName2(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField2(), map);
        updateMap("name3", accessFieldSetting.getAccFieldName3(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField3(), map);
        updateMap("name4", accessFieldSetting.getAccFieldName4(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField4(), map);
        updateMap("name5", accessFieldSetting.getAccFieldName5(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField5(), map);
        updateMap("name6", accessFieldSetting.getAccFieldName6(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField6(), map);
        updateMap("name7", accessFieldSetting.getAccFieldName7(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField7(), map);
        updateMap("name8", accessFieldSetting.getAccFieldName8(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField8(), map);
        updateMap("name9", accessFieldSetting.getAccFieldName9(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField9(), map);
        updateMap("name10", accessFieldSetting.getAccFieldName10(), Objects.isNull(accessFields) ? "" : accessFields.getAccessField10(), map);


        updateMap("no1", accessFieldSetting.getAccFieldNoName1(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo1(), map);
        updateMap("no2", accessFieldSetting.getAccFieldNoName2(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo2(), map);
        updateMap("no3", accessFieldSetting.getAccFieldNoName3(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo3(), map);
        updateMap("no4", accessFieldSetting.getAccFieldNoName4(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo4(), map);
        updateMap("no5", accessFieldSetting.getAccFieldNoName5(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo5(), map);
        updateMap("no6", accessFieldSetting.getAccFieldNoName6(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo6(), map);
        updateMap("no7", accessFieldSetting.getAccFieldNoName7(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo7(), map);
        updateMap("no8", accessFieldSetting.getAccFieldNoName8(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo8(), map);
        updateMap("no9", accessFieldSetting.getAccFieldNoName9(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo9(), map);
        updateMap("no10", accessFieldSetting.getAccFieldNoName10(), Objects.isNull(accessFields) ? 0 : accessFields.getAccessFieldNo10(), map);

        updateMap("date1", accessFieldSetting.getAccFieldDateName1(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate1(), map);
        updateMap("date2", accessFieldSetting.getAccFieldDateName2(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate2(), map);
        updateMap("date3", accessFieldSetting.getAccFieldDateName3(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate3(), map);
        updateMap("date4", accessFieldSetting.getAccFieldDateName4(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate4(), map);
        updateMap("date5", accessFieldSetting.getAccFieldDateName5(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate5(), map);
        updateMap("date6", accessFieldSetting.getAccFieldDateName6(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate6(), map);
        updateMap("date7", accessFieldSetting.getAccFieldDateName7(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate7(), map);
        updateMap("date8", accessFieldSetting.getAccFieldDateName8(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate8(), map);
        updateMap("date9", accessFieldSetting.getAccFieldDateName9(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate9(), map);
        updateMap("date10", accessFieldSetting.getAccFieldDateName10(), Objects.isNull(accessFields) ? "" : accessFields.getAccessFieldDate10(), map);

        return AccessFieldMapView.builder()
                .fields(map).build();
    }

    private void updateMap(String fieldName, String name, Object value, Map<String, AccessFieldView> map) {

        if (Objects.nonNull(name) && StringUtils.hasLength(name)) {
            AccessFieldView accessFieldView = AccessFieldView.builder()
                    .key(name).value(Objects.isNull(value) ? "" : value).build();
            map.put(fieldName, accessFieldView);
        }


//        if ("number".equalsIgnoreCase(type)) {
//            if (Objects.nonNull(name)) {
//                AccessFieldView accessFieldView = AccessFieldView.builder()
//                        .key(name).value(Objects.isNull(value) ? "" : value).build();
//                map.put(fieldName, accessFieldView);
//            }
//        }
//
//        if ("date".equalsIgnoreCase(type)) {
//            if (Objects.nonNull(name)) {
//                AccessFieldView accessFieldView = AccessFieldView.builder()
//                        .key(name).value(Objects.isNull(value) ? "" : value).build();
//                map.put(fieldName, accessFieldView);
//            }
//        }

    }

    private String getValue(AccessFieldView value) {
        return String.valueOf(Objects.isNull(value) ? "" : value.getValue());
    }

    private int getIntValue(AccessFieldView value) {
        String result = getValue(value);
        return !StringUtils.hasLength(result) ? 0 : Integer.parseInt(result);
    }

    public void saveAccessFields(AccessFieldUpdateView request, Long empNo) {
        AccessFields accessFields = getByEmpNo(empNo);

        if (Objects.isNull(accessFields)) {
            Employee employee = employeeService.getByNo(empNo);
            accessFields = AccessFields.builder()
                    .type(AFieldSettingType.EMP)
                    .accessField1(getValue(request.getName1()))
                    .accessField2(getValue(request.getName2()))
                    .accessField3(getValue(request.getName3()))
                    .accessField4(getValue(request.getName4()))
                    .accessField5(getValue(request.getName5()))
                    .accessField6(getValue(request.getName6()))
                    .accessField7(getValue(request.getName7()))
                    .accessField8(getValue(request.getName8()))
                    .accessField9(getValue(request.getName9()))
                    .accessField10(getValue(request.getName10()))
                    .accessFieldNo2(getIntValue(request.getNo2()))
                    .accessFieldNo3(getIntValue(request.getNo3()))
                    .accessFieldNo4(getIntValue(request.getNo4()))
                    .accessFieldNo5(getIntValue(request.getNo5()))
                    .accessFieldNo6(getIntValue(request.getNo6()))
                    .accessFieldNo7(getIntValue(request.getNo7()))
                    .accessFieldNo8(getIntValue(request.getNo8()))
                    .accessFieldNo9(getIntValue(request.getNo9()))
                    .accessFieldNo10(getIntValue(request.getNo10()))
                    .accessFieldDate1(dateUtil.convertToDate(getValue(request.getDate1())))
                    .accessFieldDate2(dateUtil.convertToDate(getValue(request.getDate2())))
                    .accessFieldDate3(dateUtil.convertToDate(getValue(request.getDate3())))
                    .accessFieldDate4(dateUtil.convertToDate(getValue(request.getDate4())))
                    .accessFieldDate5(dateUtil.convertToDate(getValue(request.getDate5())))
                    .accessFieldDate6(dateUtil.convertToDate(getValue(request.getDate6())))
                    .accessFieldDate7(dateUtil.convertToDate(getValue(request.getDate7())))
                    .accessFieldDate8(dateUtil.convertToDate(getValue(request.getDate8())))
                    .accessFieldDate9(dateUtil.convertToDate(getValue(request.getDate9())))
                    .accessFieldDate10(dateUtil.convertToDate(getValue(request.getDate10())))
                    .employee(employee)
                    .build();
        } else {


            if (Objects.nonNull(request.getName1()))
                accessFields.setAccessField1(getValue(request.getName1()));
            if (Objects.nonNull(request.getName2()))
                accessFields.setAccessField2(getValue(request.getName2()));
            if (Objects.nonNull(request.getName3()))
                accessFields.setAccessField3(getValue(request.getName3()));
            if (Objects.nonNull(request.getName4()))
                accessFields.setAccessField4(getValue(request.getName4()));
            if (Objects.nonNull(request.getName5()))
                accessFields.setAccessField5(getValue(request.getName5()));
            if (Objects.nonNull(request.getName6()))
                accessFields.setAccessField6(getValue(request.getName6()));
            if (Objects.nonNull(request.getName7()))
                accessFields.setAccessField7(getValue(request.getName7()));
            if (Objects.nonNull(request.getName8()))
                accessFields.setAccessField8(getValue(request.getName8()));
            if (Objects.nonNull(request.getName9()))
                accessFields.setAccessField9(getValue(request.getName9()));
            if (Objects.nonNull(request.getName10()))
                accessFields.setAccessField10(getValue(request.getName10()));

            if (Objects.nonNull(request.getNo1()))
                accessFields.setAccessFieldNo1(getIntValue(request.getNo1()));
            if (Objects.nonNull(request.getNo2()))
                accessFields.setAccessFieldNo2(getIntValue(request.getNo2()));
            if (Objects.nonNull(request.getNo3()))
                accessFields.setAccessFieldNo3(getIntValue(request.getNo3()));
            if (Objects.nonNull(request.getNo4()))
                accessFields.setAccessFieldNo4(getIntValue(request.getNo4()));
            if (Objects.nonNull(request.getNo5()))
                accessFields.setAccessFieldNo5(getIntValue(request.getNo5()));
            if (Objects.nonNull(request.getNo6()))
                accessFields.setAccessFieldNo6(getIntValue(request.getNo6()));
            if (Objects.nonNull(request.getNo7()))
                accessFields.setAccessFieldNo7(getIntValue(request.getNo7()));
            if (Objects.nonNull(request.getNo8()))
                accessFields.setAccessFieldNo8(getIntValue(request.getNo8()));
            if (Objects.nonNull(request.getNo9()))
                accessFields.setAccessFieldNo9(getIntValue(request.getNo9()));
            if (Objects.nonNull(request.getNo10()))
                accessFields.setAccessFieldNo10(getIntValue(request.getNo10()));


            if (Objects.nonNull(request.getDate1()))
                accessFields.setAccessFieldDate1(dateUtil.convertToDate(getValue(request.getDate1())));
            if (Objects.nonNull(request.getDate2()))
                accessFields.setAccessFieldDate2(dateUtil.convertToDate(getValue(request.getDate2())));
            if (Objects.nonNull(request.getDate3()))
                accessFields.setAccessFieldDate3(dateUtil.convertToDate(getValue(request.getDate3())));
            if (Objects.nonNull(request.getDate4()))
                accessFields.setAccessFieldDate4(dateUtil.convertToDate(getValue(request.getDate4())));
            if (Objects.nonNull(request.getDate5()))
                accessFields.setAccessFieldDate5(dateUtil.convertToDate(getValue(request.getDate5())));
            if (Objects.nonNull(request.getDate6()))
                accessFields.setAccessFieldDate6(dateUtil.convertToDate(getValue(request.getDate6())));
            if (Objects.nonNull(request.getDate7()))
                accessFields.setAccessFieldDate7(dateUtil.convertToDate(getValue(request.getDate7())));
            if (Objects.nonNull(request.getDate8()))
                accessFields.setAccessFieldDate8(dateUtil.convertToDate(getValue(request.getDate8())));
            if (Objects.nonNull(request.getDate9()))
                accessFields.setAccessFieldDate9(dateUtil.convertToDate(getValue(request.getDate9())));
            if (Objects.nonNull(request.getDate10()))
                accessFields.setAccessFieldDate10(dateUtil.convertToDate(getValue(request.getDate10())));


        }

        accessFieldsRepository.save(accessFields);

    }
}
