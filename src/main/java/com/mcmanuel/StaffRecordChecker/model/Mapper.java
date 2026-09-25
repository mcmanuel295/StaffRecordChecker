package com.mcmanuel.StaffRecordChecker.model;

import org.apache.commons.csv.CSVRecord;

public class Mapper {
    public static StaffRecord toStaffRecord(CSVRecord record){
        return StaffRecord.builder()
                .name(record.get("name"))
                .email(record.get("email"))
                .department(record.get("department"))
                .build();
    }
}
