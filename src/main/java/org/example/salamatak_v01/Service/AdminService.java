package org.example.salamatak_v01.Service;


import lombok.RequiredArgsConstructor;
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
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public boolean updateAdmin(Integer id, Admin newAdmin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin != null){
            oldAdmin.setName(newAdmin.getName());
            adminRepository.save(oldAdmin);
            return true;
        }
        return false;
    }

    public boolean deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if (admin != null){
            adminRepository.delete(admin);
            return true;
        }
        return false;
    }

    public String activateHospital(Integer id){
        Hospitals h = hospitalsRepository.findHospitalsById(id);
        if (h == null){
            return "this hospital are not registered yet";
        }else if (h.getIs_active().equals("accept")){
            return "this hospital is already activated";
        }else {
            h.setIs_active("accept");
            hospitalsRepository.save(h);
            return "success";
        }
    }

    public String rejectHospital(Integer id){
        Hospitals h = hospitalsRepository.findHospitalsById(id);
        if(h == null){
            return "this hospital are not registered yet";
        }else if(h.getIs_active().equals("reject")){
            return "this hospital is already rejected";
        }else {
            h.setIs_active("reject");
            hospitalsRepository.save(h);
            return "success";
        }
    }
}
