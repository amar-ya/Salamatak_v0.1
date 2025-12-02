package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Prescriptions;
import org.example.salamatak_v01.Service.PrescriptionsService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addNewPrescription(@PathVariable Integer doc_id, @PathVariable Integer id, @RequestBody @Valid Prescriptions prescription){
        prescriptionsService.addPrescription(doc_id, id, prescription);
        return ResponseEntity.status(200).body(new ApiResponse("prescription added successfully"));

    }

    @PutMapping("/update/{id}/{doc_id}")
    public ResponseEntity<?> updatePrescription(@PathVariable Integer id, @PathVariable Integer doc_id, @RequestBody @Valid Prescriptions prescription){
        prescriptionsService.updatePrescription(id, doc_id, prescription);
        return ResponseEntity.status(200).body(new ApiResponse("prescription updated successfully"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable Integer id){
        prescriptionsService.deletePrescription(id);
        return ResponseEntity.status(200).body(new ApiResponse("prescription deleted successfully"));
    }

    @GetMapping("/find-my/prescriptions/{key}")
    public ResponseEntity<?> getPrescriptionByKey(@PathVariable String key){
        return ResponseEntity.status(200).body(prescriptionsService.findMyPrescriptions(key));
    }
}
