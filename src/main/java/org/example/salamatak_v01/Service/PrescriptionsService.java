package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiException;
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

    public void addPrescription(Integer doc_id, Integer id, Prescriptions prescription){
        Doctors d = prescriptionsRepository.findDoctorById(doc_id);
        Patients p = prescriptionsRepository.findPatientById(id);
        if (d == null){
            throw new ApiException("Doctor not found");
        }
        if (p == null){
            throw new ApiException("Patient not found");
        }
        prescription.setDoctor_Id(doc_id);
        prescription.setPatient_Id(id);
        prescriptionsRepository.save(prescription);
    }

    public void updatePrescription(Integer id,Integer doc_id, Prescriptions prescription){
        Doctors d = prescriptionsRepository.findDoctorById(doc_id);
        Prescriptions p = prescriptionsRepository.findPrescriptionById(id);
        if (d == null){
            throw new ApiException("Doctor not found");
        }else if (p == null){
            throw new ApiException("Prescription not found");
        }else if (d.getId() != p.getDoctor_Id()){
            throw new ApiException("This prescription does not belong to the doctor");
        }else {
            p.setMedications(prescription.getMedications());
        }
    }

    public void deletePrescription(Integer id){
        Prescriptions p = prescriptionsRepository.findPrescriptionById(id);
        if(p == null){
            throw  new ApiException("Prescription not found");
        }else {
            prescriptionsRepository.delete(p);
        }
    }

    public List<Prescriptions> findMyPrescriptions(String Key){
        List<Prescriptions> p = prescriptionsRepository.findPrescriptionByKey(Key);
        if (p.isEmpty()){
            throw new ApiException("Prescription not found");
        }
        return p;
    }
}
