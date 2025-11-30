package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.Prescriptions;
import org.example.salamatak_v01.Repository.PrescriptionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionsService
{
    private final PrescriptionsRepository prescriptionsRepository;

    public List<Prescriptions> getAll(){
        return prescriptionsRepository.findAll();
    }

    public String addPrescription(Integer doc_id, Integer id, Prescriptions prescription){
        Doctors d = prescriptionsRepository.findDoctorById(doc_id);
        Patients p = prescriptionsRepository.findPatientById(id);
        if (d == null){
            return "Doctors not found";
        }
        if (p == null){
            return "Patient not found";
        }
        prescription.setDoctor_Id(doc_id);
        prescription.setPatient_Id(id);
        prescriptionsRepository.save(prescription);
        return "success";
    }

    public String updatePrescription(Integer id,Integer doc_id, Prescriptions prescription){
        Doctors d = prescriptionsRepository.findDoctorById(doc_id);
        Prescriptions p = prescriptionsRepository.findPrescriptionById(id);
        if (d == null){
            return "Doctors not found";
        }else if (p == null){
            return "Prescription not found";
        }else if (d.getId() != p.getDoctor_Id()){
            return "This prescription does not belong to the doctor";
        }else {
            p.setMedications(prescription.getMedications());
            return "success";
        }
    }

    public boolean deletePrescription(Integer id){
        Prescriptions p = prescriptionsRepository.findPrescriptionById(id);
        if(p == null){
            return false;
        }else {
            prescriptionsRepository.delete(p);
            return true;
        }
    }

    public List<Prescriptions> findMyPrescriptions(String Key){
        return prescriptionsRepository.findPrescriptionByKey(Key);
    }
}
