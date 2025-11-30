package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
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
    private final PatientsRepository patientsRepository;
    private final AdminRepository adminRepository;

    public List<PatientRecords> getAll(){
        return patientRecordsRepository.findAll();
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
            return true;
        }
        return false;
    }

    public void deletePatientRecord(Integer user_id){
        patientRecordsRepository.delete(patientRecordsRepository.findPatientRecordsByUserId(user_id));
    }
}
