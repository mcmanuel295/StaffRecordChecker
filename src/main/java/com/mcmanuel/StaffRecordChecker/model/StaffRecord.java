package com.mcmanuel.StaffRecordChecker.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffRecord {
    private String name;
    private String email;
    private String department;
}
