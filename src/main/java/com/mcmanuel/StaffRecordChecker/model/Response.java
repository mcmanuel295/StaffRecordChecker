package com.mcmanuel.StaffRecordChecker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private List<StaffRecord> validRecord;
    private List<InvalidRecordWithDescription> invalidRecords;
    private int validRecordCount;
    private int invalidRecordCount;
}
