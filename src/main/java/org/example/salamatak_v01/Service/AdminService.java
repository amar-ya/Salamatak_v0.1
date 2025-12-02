package org.example.salamatak_v01.Service;


import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiException;
import org.example.salamatak_v01.Model.Admin;
import org.example.salamatak_v01.Model.Hospitals;
import org.example.salamatak_v01.Repository.AdminRepository;
import org.example.salamatak_v01.Repository.HospitalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService
{
    private final AdminRepository adminRepository;
    private final HospitalsRepository hospitalsRepository;

    public List<Admin> getAll(){
        List<Admin> admins =  adminRepository.findAll();
        if (admins.isEmpty()){
            throw new ApiException("No admins found");
        }else {
            return admins;
        }
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id, Admin newAdmin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin != null){
            oldAdmin.setName(newAdmin.getName());
            adminRepository.save(oldAdmin);
        }
        throw new ApiException("No admin found");
    }

    public void deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if (admin != null){
            adminRepository.delete(admin);
        }
        throw new ApiException("there are no admins with this id");
    }

    public void activateHospital(Integer id){
        Hospitals h = hospitalsRepository.findHospitalsById(id);
        if (h == null){
            throw new ApiException("this hospital are not registered yet");
        }else if (h.getIs_active().equals("accept")){
            throw new ApiException("this hospital is already activated");
        }else {
            h.setIs_active("accept");
            hospitalsRepository.save(h);
        }
    }

    public void rejectHospital(Integer id){
        Hospitals h = hospitalsRepository.findHospitalsById(id);
        if(h == null){
            throw new ApiException("this hospital are not registered yet");
        }else if(h.getIs_active().equals("reject")){
            throw new ApiException("this hospital is already rejected");
        }else {
            h.setIs_active("reject");
            hospitalsRepository.save(h);
        }
    }
}
