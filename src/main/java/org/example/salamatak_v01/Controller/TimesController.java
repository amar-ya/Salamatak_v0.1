package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Times;
import org.example.salamatak_v01.Service.TimesService;
import org.example.salamatak_v01.Service.VisitRecordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/time")
@RequiredArgsConstructor
public class TimesController
{
    private final TimesService timesService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllTimes(){
        List<Times> timesList = timesService.getAll();
        if (timesList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No times found"));
        }else {
            return ResponseEntity.status(200).body(timesList);
        }
    }

    @PostMapping("/add/{doc_id}")
    public ResponseEntity<?> addSession(@PathVariable Integer doc_id, @RequestBody @Valid Times times){
        timesService.addTimes(doc_id, times);
        return ResponseEntity.status(200).body(new ApiResponse("Session added successfully"));
    }

    @PutMapping("/update/{id}/{doc_id}")
    public ResponseEntity<?> updateTime(@PathVariable Integer id, @PathVariable Integer doc_id, @RequestBody @Valid Times times){
        timesService.updateTimes(id, doc_id, times);
        return ResponseEntity.status(200).body(new ApiResponse("Session updated successfully"));
    }

    @DeleteMapping("/delete/{id}/{doc_id}")
    public ResponseEntity<?> deleteTime(@PathVariable Integer id, @PathVariable Integer doc_id){
        timesService.deleteTimes(id, doc_id);
        return ResponseEntity.status(200).body(new ApiResponse("Session deleted successfully"));
    }

    @GetMapping("/get/avaliable-sessions/{speciality}")
    public ResponseEntity<?> getAvaliableSessions(@PathVariable String speciality){
        List<Times> timesList = timesService.findAvaliableTimes(speciality);
        if (timesList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No sessions found"));
        }else {
            return ResponseEntity.status(200).body(timesList);
        }
    }

    @PutMapping("/reserve/session-time/{id}/{key}/{reason}")
    public ResponseEntity<?> reserveSessionTime(@PathVariable Integer id, @PathVariable String key, @PathVariable String reason){
        timesService.reserveSession(id,key,reason);
        return ResponseEntity.status(200).body(new ApiResponse("Session reserved successfully"));
    }

    @PostMapping("/doctor-working/hours/{doc_id}/{start}/{end}")
    public ResponseEntity<?> addDoctorWorking(@PathVariable Integer doc_id,@PathVariable Integer start, @PathVariable Integer end){
        timesService.setWorkingHours(doc_id,start,end);
        return ResponseEntity.status(200).body(new ApiResponse("WorkingHours added successfully"));
    }

    @PutMapping("/cancel/reservation/{id}/{key}")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer id, @PathVariable String key){
        timesService.cancelReservation(id,key);
        return ResponseEntity.status(200).body(new ApiResponse("reservation canceled successfully"));
    }

}
