package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiException;
import org.example.salamatak_v01.Model.PatientRecords;
import org.example.salamatak_v01.Repository.AdminRepository;
import org.example.salamatak_v01.Repository.PatientRecordsRepository;
import org.example.salamatak_v01.Repository.PatientsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientRecordsService
{
    private final PatientRecordsRepository patientRecordsRepository;


    public List<PatientRecords> getAll(){
        List<PatientRecords> pr = patientRecordsRepository.findAll();
        if (pr.isEmpty())
        {
            throw new ApiException("Hospitals not found");
        }else
        {
            return pr;
        }
    }

    public void addPatientRecord(Integer user_id){
        PatientRecords p = new PatientRecords();
        patientRecordsRepository.save(p);
    }



    public boolean updatePatientRecord(Integer id, PatientRecords patientRecords){
        PatientRecords records = patientRecordsRepository.findPatientRecordsById(id);
        if (records != null){
            records.setAllergies(patientRecords.getAllergies());
            records.setHeight(patientRecords.getHeight());
            records.setWeight(patientRecords.getWeight());
            records.setBmi(patientRecords.getWeight()/(patientRecords.getHeight()*records.getHeight()));
            records.setTemperature(patientRecords.getTemperature());
            records.setHeart_rate(patientRecords.getHeart_rate());
            records.setBlood_pressure(patientRecords.getBlood_pressure());
            patientRecordsRepository.save(records);
        }
        throw new ApiException("record not found");
    }

    public void deletePatientRecord(Integer user_id){
        PatientRecords pr = patientRecordsRepository.findPatientRecordsByUserId(user_id);
        if (pr == null){
            throw new ApiException("record not found");
        }
        patientRecordsRepository.delete(pr);
    }
}
