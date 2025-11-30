package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.VisitRecords;
import org.example.salamatak_v01.Service.DoctorsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
        List<Doctors> docs = doctorsService.getAll();
        if (docs.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there are no doctors added yet"));
        }else {
            return ResponseEntity.status(200).body(docs);
        }
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addDoctor(@PathVariable Integer id ,@RequestBody @Valid Doctors doctors, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else{
            String result = doctorsService.addDoctor(id, doctors);
            if (result.equals("success")){
                return ResponseEntity.status(200).body(new ApiResponse("doctor added successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("doctor add failed"));
            }
        }
    }

    @PutMapping("/update/{doc_id}/{hos_id}")
    public ResponseEntity<?> updateDoctorProfile(@PathVariable Integer doc_id, @PathVariable Integer hos_id, @RequestBody @Valid Doctors doctors, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = doctorsService.updateDoctor(doc_id,hos_id,doctors);
            if (result.equals("success")){
                return ResponseEntity.status(200).body(new ApiResponse("doctor updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }

    @DeleteMapping("/delete/{doc_id}/{hos_id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer doc_id,@PathVariable Integer hos_id){
        String result = doctorsService.deleteDoctor(doc_id,hos_id);
        if (result.equals("success")){
            return ResponseEntity.status(200).body(new ApiResponse("doctor deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }


    @PutMapping("/finish-session/{id}/{doc_id}")
    public ResponseEntity<?> finishSession(@PathVariable Integer id, @PathVariable Integer doc_id, @RequestBody @Valid VisitRecords visit,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = doctorsService.addComment(id, doc_id, visit);
            if (result.equals("success")){
                return ResponseEntity.status(200).body(new ApiResponse("doctor finished session successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }
}
