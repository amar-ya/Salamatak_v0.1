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
        return ResponseEntity.status(200).body(adminService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("admin added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody @Valid Admin admin){
        adminService.updateAdmin(id, admin);
        return ResponseEntity.status(200).body(new ApiResponse("admin updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body(new ApiResponse("admin deleted successfully"));
    }

    @PutMapping("/activate-hospital/{id}")
    public ResponseEntity<?> activateHospital(@PathVariable Integer id){
        adminService.activateHospital(id);
        return ResponseEntity.status(200).body(new ApiResponse("hospital activated successfully"));
    }

    @PutMapping("/reject-hospital/{id}")
    public ResponseEntity<?> rejectHospital(@PathVariable Integer id){
        adminService.rejectHospital(id);
        return ResponseEntity.status(200).body(new ApiResponse("hospital rejected successfully"));
    }
}
