package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.PatientRecords;
import org.example.salamatak_v01.Service.PatientRecordsService;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.status(200).body(patientRecordsService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatientRecord(@PathVariable Integer id, @RequestBody @Valid PatientRecords patientRecords){
        patientRecordsService.updatePatientRecord(id,patientRecords);
        return ResponseEntity.status(200).body(new ApiResponse("update success"));

    }

}