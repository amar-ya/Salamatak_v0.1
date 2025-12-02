package org.example.salamatak_v01.Controller;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Service.SickLeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/sick-leave")
@RequiredArgsConstructor
public class SickLeavesController
{
    private final SickLeaveService sickLeaveService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllSickLeaves(){
        return ResponseEntity.status(200).body(sickLeaveService.getAll());
    }

    @GetMapping("/find-my/sick-leave/{key}")
    public ResponseEntity<?> getMySickLeaves(@PathVariable Integer key){
        return ResponseEntity.status(200).body(sickLeaveService.getMySickLeaves(key));
    }

    @PostMapping("/make/sick-leave/{doc_id}/{patient_id}/{date}/{days}")
    public ResponseEntity<?> createSickLeave(@PathVariable Integer doc_id, @PathVariable Integer patient_id,
                                             @PathVariable Date date, @PathVariable Integer days){
        sickLeaveService.createSickLeave(doc_id, patient_id, date, days);
        return ResponseEntity.status(200).body(new ApiResponse("sick leave created successfully"));

    }
}
