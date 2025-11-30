package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Prescriptions;
import org.example.salamatak_v01.Service.PrescriptionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
public class PrescriptionsController
{
    private final PrescriptionsService prescriptionsService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){
        List<Prescriptions> p = prescriptionsService.getAll();
        if (p.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no prescriptions added yet"));
        }else {
            return ResponseEntity.status(200).body(p);
        }
    }

    @PostMapping("/add/{doc_id}/{id}")
    public ResponseEntity<?> addNewPrescription(@PathVariable Integer doc_id, @PathVariable Integer id, @RequestBody @Valid Prescriptions prescription, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = prescriptionsService.addPrescription(doc_id, id, prescription);
            if (result.equals("success")){
                return ResponseEntity.status(200).body(new ApiResponse("prescription added successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }

    @PutMapping("/update/{id}/{doc_id}")
    public ResponseEntity<?> updatePrescription(@PathVariable Integer id, @PathVariable Integer doc_id, @RequestBody @Valid Prescriptions prescription, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = prescriptionsService.updatePrescription(id, doc_id, prescription);
            if (result.equals("success")){
                return ResponseEntity.status(200).body(new ApiResponse("prescription updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable Integer id){
        boolean result = prescriptionsService.deletePrescription(id);
        if (result){
            return ResponseEntity.status(200).body(new ApiResponse("prescription deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("prescription not found"));
        }
    }

    @GetMapping("/find-my/prescriptions/{key}")
    public ResponseEntity<?> getPrescriptionByKey(@PathVariable String key){
        List<Prescriptions> p = prescriptionsService.findMyPrescriptions(key);
        if (p.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("you dont have any prescriptions"));
        }else {
            return ResponseEntity.status(200).body(p);
        }
    }
}
