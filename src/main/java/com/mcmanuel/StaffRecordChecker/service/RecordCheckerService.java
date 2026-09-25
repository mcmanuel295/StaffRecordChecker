package com.mcmanuel.StaffRecordChecker.service;

import com.mcmanuel.StaffRecordChecker.model.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

@Service
public class RecordCheckerService {


    public Response checkRecords(MultipartFile csvFile){

        try{
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream());
        }
        catch (){

        }
    }
}
