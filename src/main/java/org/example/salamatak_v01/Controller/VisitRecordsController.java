package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Model.VisitRecords;
import org.example.salamatak_v01.Service.VisitRecordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/visit-record")
@RequiredArgsConstructor
public class VisitRecordsController
{
    private final VisitRecordsService visitRecordsService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllVisitRecords(){
        return ResponseEntity.status(200).body(visitRecordsService.getAll());
    }

    @PostMapping("/add/{doc_id}")
    public ResponseEntity<?> addVisitRecord(@PathVariable Integer doc_id, @RequestBody @Valid VisitRecords visitRecords){
        visitRecordsService.addVisitRecords(doc_id, visitRecords);
        return ResponseEntity.status(200).body("visit record registered successfully");

    }

    @PutMapping("/update/{visit_id}/{doc_id}")
    public ResponseEntity<?> updateVisitRecord(@PathVariable Integer visit_id, @PathVariable Integer doc_id, @RequestBody @Valid VisitRecords visitRecords){
        visitRecordsService.updateVisitRecords(visit_id, doc_id, visitRecords);
        return ResponseEntity.status(200).body("visit record updated successfully");
    }

    @DeleteMapping("/delete/{id}/{doc_id}")
    public ResponseEntity<?> deleteVisitRecord(@PathVariable Integer id, @PathVariable Integer doc_id){
        visitRecordsService.deleteVisitRecords(id, doc_id);
        return ResponseEntity.status(200).body("visit record deleted successfully");
    }


}
