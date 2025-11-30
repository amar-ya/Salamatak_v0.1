package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Times;
import org.example.salamatak_v01.Service.TimesService;
import org.example.salamatak_v01.Service.VisitRecordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/api/v1/time")
@RequiredArgsConstructor
public class TimesController
{
    private final TimesService timesService;
    private final VisitRecordsService visitRecordsService;

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
    public ResponseEntity<?> addSession(@PathVariable Integer doc_id, @RequestBody @Valid Times times, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = timesService.addTimes(doc_id, times);
            if (result.equals("success")) {
                return ResponseEntity.status(200).body(new ApiResponse("Session added successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }

    @PutMapping("/update/{id}/{doc_id}")
    public ResponseEntity<?> updateTime(@PathVariable Integer id, @PathVariable Integer doc_id, @RequestBody @Valid Times times,Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = timesService.updateTimes(id, doc_id, times);
            if (result.equals("success")) {
                return ResponseEntity.status(200).body(new ApiResponse("Session updated successfully"));
            }else{
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }

    @DeleteMapping("/delete/{id}/{doc_id}")
    public ResponseEntity<?> deleteTime(@PathVariable Integer id, @PathVariable Integer doc_id){
        String result = timesService.deleteTimes(id, doc_id);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Session deleted successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
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
        String result = timesService.reserveSession(id,key,reason);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("Session reserved successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

    @PostMapping("/doctor-working/hours/{doc_id}/{start}/{end}")
    public ResponseEntity<?> addDoctorWorking(@PathVariable Integer doc_id,@PathVariable Integer start, @PathVariable Integer end){
        String result = timesService.setWorkingHours(doc_id,start,end);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("WorkingHours added successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

    @PutMapping("/cancel/reservation/{id}/{key}")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer id, @PathVariable String key){
        String result = timesService.cancelReservation(id,key);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("reservation canceled successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

}
