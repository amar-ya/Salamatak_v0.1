package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Hospitals;
import org.example.salamatak_v01.Service.HospitalsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/hospital")
@RequiredArgsConstructor
public class HospitalsController
{
    private final HospitalsService hospitalsService;

    @GetMapping("/get")
    public ResponseEntity<?> getHospitals(){
        List<Hospitals> h = hospitalsService.getAll();
        if(h.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there are no registered hospitals yet"));
        }else {
            return ResponseEntity.status(200).body(h);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHospital(@RequestBody @Valid Hospitals hospitals, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            hospitalsService.addHospital(hospitals);
            return ResponseEntity.status(200).body(new ApiResponse("hospital added successfully and it has to be activated by an admin"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHospital(@PathVariable Integer id, @RequestBody @Valid Hospitals hospitals,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            boolean result = hospitalsService.updateHospital(id, hospitals);
            if(result){
                return ResponseEntity.status(200).body(new ApiResponse("hospital updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("hospital hospital not found"));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHospital(@PathVariable Integer id){
        boolean result = hospitalsService.deleteHospital(id);
        if(result){
            return ResponseEntity.status(200).body(new ApiResponse("hospital deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("hospital not found"));
        }
    }
}
