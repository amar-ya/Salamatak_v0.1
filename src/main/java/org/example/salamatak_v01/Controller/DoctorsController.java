package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.VisitRecords;
import org.example.salamatak_v01.Service.DoctorsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorsController
{
    private final DoctorsService doctorsService;

    @GetMapping("/get")
    public ResponseEntity<?> getDoctors(){
        return ResponseEntity.status(200).body(doctorsService.getAll());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addDoctor(@PathVariable Integer id ,@RequestBody @Valid Doctors doctors){
        doctorsService.addDoctor(id, doctors);
        return ResponseEntity.status(200).body(new ApiResponse("doctor added successfully"));
    }

    @PutMapping("/update/{doc_id}/{hos_id}")
    public ResponseEntity<?> updateDoctorProfile(@PathVariable Integer doc_id, @PathVariable Integer hos_id, @RequestBody @Valid Doctors doctors){
        doctorsService.updateDoctor(doc_id,hos_id,doctors);
        return ResponseEntity.status(200).body(new ApiResponse("doctor updated successfully"));
    }

    @DeleteMapping("/delete/{doc_id}/{hos_id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer doc_id,@PathVariable Integer hos_id){
        doctorsService.deleteDoctor(doc_id,hos_id);
        return ResponseEntity.status(200).body(new ApiResponse("doctor deleted successfully"));
    }


    @PutMapping("/finish-session/{id}/{doc_id}")
    public ResponseEntity<?> finishSession(@PathVariable Integer id, @PathVariable Integer doc_id, @RequestBody @Valid VisitRecords visit){
        doctorsService.addComment(id, doc_id, visit);
        return ResponseEntity.status(200).body(new ApiResponse("doctor finished session successfully"));
    }
}
