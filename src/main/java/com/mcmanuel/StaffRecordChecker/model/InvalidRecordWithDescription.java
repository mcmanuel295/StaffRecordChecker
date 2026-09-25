package com.mcmanuel.StaffRecordChecker.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InvalidRecordWithDescription {
    private List<String> recordValues;
    String description;
}
