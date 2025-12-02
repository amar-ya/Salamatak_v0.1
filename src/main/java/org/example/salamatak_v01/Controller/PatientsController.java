package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Service.PatientRecordsService;
import org.example.salamatak_v01.Service.PatientsService;
import org.example.salamatak_v01.Service.UsersKeysService;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.status(200).body(patientsService.getPatients());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPatient(@RequestBody @Valid Patients patients){
        patientsService.addPatient(patients);
        usersKeysService.addPatientKeys(patients.getId());
        patientRecordsService.addPatientRecord(patients.getId());
        return ResponseEntity.status(200).body(new ApiResponse("account registered successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Integer id, @RequestBody @Valid Patients patients){
        patientsService.updatePatient(id, patients);
        return ResponseEntity.status(200).body(new ApiResponse("account updated successfully"));
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer user_id){
        patientsService.deletePatient(user_id);
        patientRecordsService.deletePatientRecord(user_id);
        return ResponseEntity.status(200).body(new ApiResponse("account deleted successfully"));
    }

    @GetMapping("/ai/health-advice/{key}")
    public ResponseEntity<?> getHealthAdvice(@PathVariable String key){
        String result = patientsService.getHealthAdvice(key);
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/ai/explain-feeling")
    public ResponseEntity<?> getFeelingAdvice(@RequestBody String feeling){
        String result = patientsService.getFeelingAdvice(feeling);
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/forgot-password/{id}")
    public ResponseEntity<?> forgotPassword(@PathVariable Integer id){
        usersKeysService.generatePatientOTP(id);
        return ResponseEntity.status(200).body(new ApiResponse("OTP sent via what`s app"));

    }

    @PutMapping("/change-password/{otp}/{national}/{password}")
    public ResponseEntity<?> changePassword(@PathVariable String otp, @PathVariable String national, @PathVariable String password){
        patientsService.changePassword(otp, national, password);
        return ResponseEntity.status(200).body(new ApiResponse("password changed successfully"));
    }

    @GetMapping("/login/{national}/{password}")
    public ResponseEntity<?> login(@PathVariable String national, @PathVariable String password){
        String key = patientsService.login(national, password);
        return ResponseEntity.status(200).body(new ApiResponse(key));

    }

    @DeleteMapping("/log-out/{key}")
    public ResponseEntity<?> logout(@PathVariable String key){
        patientsService.logOut(key);
        return ResponseEntity.status(200).body(new ApiResponse("logout successfully"));
    }

    @GetMapping("/what-to-ask/{key}")
    public ResponseEntity<?> whatToAsk(@PathVariable String key,@RequestBody String feeling){
        String result = patientsService.prepareQuestionsToAsksDoctor(key,feeling);
        return ResponseEntity.status(200).body(result);
    }
}
