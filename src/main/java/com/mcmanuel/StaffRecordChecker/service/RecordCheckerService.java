package com.mcmanuel.StaffRecordChecker.service;

import com.mcmanuel.StaffRecordChecker.model.InvalidRecordWithDescription;
import com.mcmanuel.StaffRecordChecker.model.Mapper;
import com.mcmanuel.StaffRecordChecker.model.Response;
import com.mcmanuel.StaffRecordChecker.model.StaffRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordCheckerService {


    public Response checkRecords(MultipartFile csvFile){
        if (csvFile == null) {
            throw new RuntimeException("file is empty ");
        }
        List<StaffRecord> validRecordList = new ArrayList<>();
        List<InvalidRecordWithDescription> invalidRecordList = new ArrayList<>();

        try{
            Reader reader = new InputStreamReader(csvFile.getInputStream());
            CSVParser parser = CSVFormat.DEFAULT.parse(reader);

            for(CSVRecord record : parser){
                List<String> emailList = validRecordList.stream().map(StaffRecord::getEmail).toList();

                System.out.println("record: "+record.toList() );
                String email = record.get("email");

//                this checks if the record has the 3 columns
                if (record.isConsistent()) {

//                    this checks if the email is valid (contains @example.com)
                    if( isEmailValid(email)){

//                    this checks if the email does not have duplicates, if no duplicate, it creates a record as valid otherwise it creates an invalid record with description
                        if (!isDuplicate(email, emailList)){
                            StaffRecord staffRecord = Mapper.toStaffRecord(record);
                            validRecordList.add(staffRecord);
                        }
                        else {
                            InvalidRecordWithDescription invalidRecord = InvalidRecordWithDescription.builder()
                                    .recordValues(
                                            record.toList()).description("Duplicate email").build();
                            invalidRecordList.add(invalidRecord);
                        }

                    }
                    else {
                        InvalidRecordWithDescription invalidRecord = InvalidRecordWithDescription.builder()
                                .recordValues(
                                        record.toList()).description("Invalid email").build();
                        invalidRecordList.add(invalidRecord);
                    }

                }

//              if the record is incomplete i.e it has 2 columns
                else {
                    if (!record.get(2).contains("@example.com")) {
                        InvalidRecordWithDescription invalidRecord = InvalidRecordWithDescription.builder()
                                .recordValues(
                                        record.toList()).description("Name or email missing or invalid email").build();
                        invalidRecordList.add(invalidRecord);
                    }
                    else {
                        StaffRecord staffRecord = Mapper.toStaffRecord(record);
                        validRecordList.add(staffRecord);
                    }
                }
            }

            return Response.builder()
                    .validRecord(validRecordList)
                    .invalidRecords(invalidRecordList)
                    .validRecordCount(validRecordList.size())
                    .invalidRecordCount(invalidRecordList.size())
                    .build();
        }
        catch (IOException ex) {
            System.out.println("IOException : "+ex.getMessage());
                throw new RuntimeException(ex);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("a2example.com");
    }

    private boolean isDuplicate(String email, List<String> emailList) {
        return emailList.contains(email);
    }


}
