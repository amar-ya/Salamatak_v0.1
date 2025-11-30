package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Service.PatientRecordsService;
import org.example.salamatak_v01.Service.PatientsService;
import org.example.salamatak_v01.Service.UsersKeysService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/patient")
@RequiredArgsConstructor
public class PatientsController
{
    private final PatientsService patientsService;
    private final PatientRecordsService patientRecordsService;
    private final UsersKeysService usersKeysService;

    @GetMapping("/get")
    public ResponseEntity<?> getPatients(){
        List<Patients> p = patientsService.getPatients();
        if(p.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no registered patient found"));
        }else{
            return ResponseEntity.status(200).body(p);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPatient(@RequestBody @Valid Patients patients, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String result = patientsService.addPatient(patients);
            if (result.equals("success")) {
                usersKeysService.addPatientKeys(patients.getId());
                patientRecordsService.addPatientRecord(patients.getId());
                return ResponseEntity.status(200).body(new ApiResponse("account registered successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse(result));
            }
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Integer id, @RequestBody @Valid Patients patients,Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            boolean result = patientsService.updatePatient(id, patients);
            if (result) {
                return ResponseEntity.status(200).body(new ApiResponse("account updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("user not found"));
            }
        }
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer user_id){
        boolean result = patientsService.deletePatient(user_id);
        if (result) {
            patientRecordsService.deletePatientRecord(user_id);
            return ResponseEntity.status(200).body(new ApiResponse("account deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        }
    }

    @GetMapping("/ai/health-advice/{key}")
    public ResponseEntity<?> getHealthAdvice(@PathVariable String key){
        String result = patientsService.getHealthAdvice(key);
        return ResponseEntity.status(200).body(new ApiResponse(result));
    }

    @GetMapping("/ai/explain-feeling")
    public ResponseEntity<?> getFeelingAdvice(@RequestBody String feeling){
        String result = patientsService.getFeelingAdvice(feeling);
        return ResponseEntity.status(200).body(new ApiResponse(result));
    }

    @PostMapping("/forgot-password/{id}")
    public ResponseEntity<?> forgotPassword(@PathVariable Integer id){
        String result = usersKeysService.generatePatientOTP(id);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("OTP sent via what`s app"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

    @PutMapping("/change-password/{otp}/{national}/{password}")
    public ResponseEntity<?> changePassword(@PathVariable String otp, @PathVariable String national, @PathVariable String password){
        String result = patientsService.changePassword(otp, national, password);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("password changed successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

    @GetMapping("/login/{national}/{password}")
    public ResponseEntity<?> login(@PathVariable String national, @PathVariable String password){
        String result = patientsService.login(national, password);
        if (result.contains("success")) {
            return ResponseEntity.status(200).body(new ApiResponse(result));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

    @DeleteMapping("/log-out/{key}")
    public ResponseEntity<?> logout(@PathVariable String key){
        String result = patientsService.logOut(key);
        if (result.equals("success")) {
            return ResponseEntity.status(200).body(new ApiResponse("logout successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

    @GetMapping("/what-to-ask/{key}")
    public ResponseEntity<?> whatToAsk(@PathVariable String key,@RequestBody String feeling){
        String result = patientsService.prepareQuestionsToAsksDoctor(key,feeling);
        if (result.equals("failed")) {
            return ResponseEntity.status(400).body(new ApiResponse("you dont have any session reserved"));
        }else {
            return ResponseEntity.status(200).body(new ApiResponse(result));
        }
    }
}
