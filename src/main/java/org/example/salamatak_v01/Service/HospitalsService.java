package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Model.Hospitals;
import org.example.salamatak_v01.Repository.HospitalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalsService
{
    private final HospitalsRepository hospitalsRepository;

    public List<Hospitals> getAll(){
        return hospitalsRepository.findAll();
    }

    public void addHospital(Hospitals hospitals){
        hospitals.setIs_active(false);
        hospitalsRepository.save(hospitals);
    }

    public boolean updateHospital(Integer id, Hospitals hospitals){
        Hospitals h = hospitalsRepository.findHospitalsById(id);
        if (h != null){
            h.setCity(hospitals.getCity());
            h.setName(hospitals.getName());
            h.setPhone(hospitals.getPhone());
            hospitalsRepository.save(h);
            return true;
        }
        return false;
    }

    public boolean deleteHospital(Integer id){
        Hospitals h = hospitalsRepository.findHospitalsById(id);
        if (h != null){
            hospitalsRepository.delete(h);
            return true;
        }
        return false;
    }
}
