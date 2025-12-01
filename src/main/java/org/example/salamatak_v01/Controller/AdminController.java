package org.example.salamatak_v01.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiResponse;
import org.example.salamatak_v01.Model.Admin;
import org.example.salamatak_v01.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminController
{
    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity<?> getAdmins(){
        List<Admin> admin = adminService.getAll();
        if (admin.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there are not admins yet"));
        }else {
            return ResponseEntity.status(200).body(admin);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid Admin admin, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else{
            adminService.addAdmin(admin);
            return ResponseEntity.status(200).body(new ApiResponse("admin added successfully"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody @Valid Admin admin, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            boolean result = adminService.updateAdmin(id, admin);
            if (result){
                return ResponseEntity.status(200).body(new ApiResponse("admin updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("admin not found"));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        boolean result = adminService.deleteAdmin(id);
        if (result){
            return ResponseEntity.status(200).body(new ApiResponse("admin deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("admin not found"));
        }
    }

    @PutMapping("/activate-hospital/{id}")
    public ResponseEntity<?> activateHospital(@PathVariable Integer id){
        String result = adminService.activateHospital(id);
        if (result.equals("success")){
            return ResponseEntity.status(200).body(new ApiResponse("hospital activated successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }

    @PutMapping("/reject-hospital/{id}")
    public ResponseEntity<?> rejectHospital(@PathVariable Integer id){
        String result = adminService.rejectHospital(id);
        if (result.equals("success")){
            return ResponseEntity.status(200).body(new ApiResponse("hospital rejected successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }
    }
}
