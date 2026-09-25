package com.mcmanuel.StaffRecordChecker.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Response {
    private List<StaffRecord> validRecord;
    private List<InvalidRecordWithDescription> invalidRecords;
    private int validRecordCount;
    private int invalidRecordCount;
}
