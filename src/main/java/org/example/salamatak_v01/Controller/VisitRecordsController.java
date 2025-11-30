package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.VisitRecords;
import org.example.salamatak_v01.Service.VisitRecordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visit-record")
@RequiredArgsConstructor
public class VisitRecordsController
{
    private final VisitRecordsService visitRecordsService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllVisitRecords(){
        List<VisitRecords> visitRecords = visitRecordsService.getAll();
        if (visitRecords.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse( "No records found"));
        }else {
            return ResponseEntity.status(200).body(visitRecords);
        }
    }

    @PostMapping("/add/{doc_id}")
    public ResponseEntity<?> addVisitRecord(@PathVariable Integer doc_id, @RequestBody @Valid VisitRecords visitRecords, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = visitRecordsService.addVisitRecords(doc_id, visitRecords);
            if (result.equals("success")) {
                return ResponseEntity.status(200).body("visit record registered successfully");
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }

    }

    @PutMapping("/update/{visit_id}/{doc_id}")
    public ResponseEntity<?> updateVisitRecord(@PathVariable Integer visit_id, @PathVariable Integer doc_id, @RequestBody @Valid VisitRecords visitRecords,Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = visitRecordsService.updateVisitRecords(visit_id, doc_id, visitRecords);
            if (result.equals("success")) {
                return ResponseEntity.status(200).body("visit record updated successfully");
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }

    @DeleteMapping("/delete/{id}/{doc_id}")
    public ResponseEntity<?> deleteVisitRecord(@PathVariable Integer id, @PathVariable Integer doc_id){
        String result = visitRecordsService.deleteVisitRecords(id, doc_id);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body("visit record deleted successfully");
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }


}
