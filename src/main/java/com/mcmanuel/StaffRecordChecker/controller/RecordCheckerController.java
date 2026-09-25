package com.mcmanuel.StaffRecordChecker.controller;

import com.mcmanuel.StaffRecordChecker.model.Response;
import com.mcmanuel.StaffRecordChecker.service.RecordCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class RecordCheckerController {
    private final RecordCheckerService service;


    @PostMapping("/check")
    public ResponseEntity<Response> checkRecord(@RequestParam MultipartFile csvFile){
        try{
            Response response = service.checkRecords(csvFile);
            return ResponseEntity.ok().body(response);

        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
