package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.PatientRecords;
import org.example.salamatak_v01.Service.PatientRecordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient-record")
@RequiredArgsConstructor
public class PatientRecordsController
{
    private final PatientRecordsService patientRecordsService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllRecords(){
        List<PatientRecords> pr = patientRecordsService.getAll();
        if(pr.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No records found"));
        }else {
            return ResponseEntity.status(200).body(pr);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatientRecord(@PathVariable Integer id, @RequestBody @Valid PatientRecords patientRecords, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else{
            boolean result = patientRecordsService.updatePatientRecord(id,patientRecords);
            if(result){
                return ResponseEntity.status(200).body("update success");
            }else{
                return ResponseEntity.status(400).body(new ApiResponse("user record not found"));
            }
        }
    }

}