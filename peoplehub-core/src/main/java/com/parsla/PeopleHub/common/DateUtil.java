package com.parsla.PeopleHub.common;


import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

@Component
public class DateUtil {

    public static String DATE_FORMAT_OUTPUT = "dd/MM/yyyy";

     public LocalDate convert(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern((DATE_FORMAT_OUTPUT)));
    }
    public Date convertToDate(String date) {

        if(date == null || date.equals("null") || date.equals("") ) return null;
        OffsetDateTime odt = OffsetDateTime.parse(date);
        LocalDateTime ldt = odt.toLocalDateTime();
        return convertToDateViaInstant(ldt);
//

//        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(DATE_FORMAT_OUTPUT)
//                .parseDefaulting(ChronoField.YEAR, 2017)
//                .toFormatter();
//        LocalDate parsedDate = LocalDate.parse(date, formatter);
//        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/d/yyyy");
//        String formattedStringDate = parsedDate.format(formatter2);
//
//        ZoneId defaultZoneId = ZoneId.systemDefault();
//        //LocalDate localDate = convert(date);
//        return Date.from(parsedDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
