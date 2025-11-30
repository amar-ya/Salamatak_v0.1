package org.example.salamatak_v01.Controller;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.SickLeave;
import org.example.salamatak_v01.Service.SickLeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sick-leave")
@RequiredArgsConstructor
public class SickLeavesController
{
    private final SickLeaveService sickLeaveService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllSickLeaves(){
        List<SickLeave> leaves = sickLeaveService.getAll();
        if (leaves.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no sick leaves found"));
        }else {
            return ResponseEntity.status(200).body(leaves);
        }
    }

    @GetMapping("/find-my/sick-leave/{key}")
    public ResponseEntity<?> getMySickLeaves(@PathVariable Integer key){
        List<SickLeave> leaves = sickLeaveService.getMySickLeaves(key);
        if (leaves.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("you dont have any sick leaves"));
        }else {
            return ResponseEntity.status(200).body(leaves);
        }
    }

    @PostMapping("/make/sick-leave/{doc_id}/{patient_id}/{date}/{days}")
    public ResponseEntity<?> createSickLeave(@PathVariable Integer doc_id, @PathVariable Integer patient_id,
                                             @PathVariable Date date, @PathVariable Integer days){
        String result = sickLeaveService.createSickLeave(doc_id, patient_id, date, days);
        if (result.equals("success")){
            return ResponseEntity.status(200).body(new ApiResponse("sick leave created successfully"));
        }else {
            return ResponseEntity.status(400).body(result);
        }
    }
}
