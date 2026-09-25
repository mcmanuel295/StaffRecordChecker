package com.mcmanuel.StaffRecordChecker.service;

import com.mcmanuel.StaffRecordChecker.model.Response;
import com.mcmanuel.StaffRecordChecker.model.StaffRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordCheckerService {


    public Response checkRecords(MultipartFile csvFile){
        Response response = new Response();

        List<StaffRecord> validRecords = new ArrayList<>();

        List<String> emailList = validRecords.stream().map(StaffRecord::getEmail).toList();
        try{
            BufferedInputStream bufferedInputStream = new BufferedInputStream(csvFile.getInputStream());

            return response;
        }
        catch (IOException ex) {
            System.out.println("IOException : "+ex.getMessage());
                throw new RuntimeException(ex);
        }
    }
}
