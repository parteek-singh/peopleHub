package com.parsla.PeopleHub.common;

import com.parsla.PeopleHub.constant.enums.ObjectType;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NumberUtil {

    public long generateIndexNo(ObjectType type, long numOfRecords) {
        int index = 0;
        switch (type){
            case COMPANY -> index = 1;
            case DEPARTMENT -> index = 2;
            case ACCOUNT -> index = 3;
            case EMPLOYEE -> index = 4;
            case DOCUMENT -> index = 5;
            case ACCESS_FIELD -> index = 6;

        }
       return generateUniqueId(index, numOfRecords);
    }


    private long generateUniqueId(int index, long count){
        Random rand = new Random();
        int random = rand.nextInt(900) + 100;

        // existing depart count
        String deptCountStr = String.format("%05d", count);
        // "2- RAND(3) - 10000"
        return Long.parseLong(String.valueOf(index + random  + deptCountStr));
    }

}
