package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiException;
import org.example.salamatak_v01.Model.*;
import org.example.salamatak_v01.Repository.SickLeaveRepository;
import org.example.salamatak_v01.Repository.UsersKeyRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SickLeaveService
{
    private final SickLeaveRepository leaveRepository;
    private final UsersKeyRepository usersKeyRepository;

    public List<SickLeave> getAll(){
        List<SickLeave> leaves = leaveRepository.findAll();
        if (leaves.isEmpty())
        {
            throw new ApiException("no sick leaves found");
        }
        return leaves;
    }

    public void addSickLeave(SickLeave sickLeave){
        leaveRepository.save(sickLeave);
    }

    public List<SickLeave> getMySickLeaves(Integer key){
        Patients p = usersKeyRepository.findPatientByKeyId(key);
        List<SickLeave> leaves = leaveRepository.findSickLeavesByPatient_id(p.getId());
        if (leaves.isEmpty()){
            throw new ApiException("you dont have any sick leave");
        }else if(p == null){
            throw new ApiException("patient not found");
        }
        return leaves;
    }

    public void createSickLeave(Integer doc_id, Integer patient_id, Date start_date, Integer days){
        Patients p = leaveRepository.findPatientBySickLeaveId(patient_id);
        Doctors d = leaveRepository.findDoctorBySickLeaveId(doc_id);
        Times records = leaveRepository.findVisitByPatientId(patient_id);
        if (p == null){
            throw new ApiException("patient not found");
        }
        if (d == null){
            throw new ApiException("doctor not found");
        }
        if (start_date == null){
            throw new ApiException("failed making sick leave due to the lack of start date");
        }
        if (records == null){
            throw new ApiException("patient didnt visit you");
        }
        SickLeave leave = new SickLeave();
        leave.setDoctor_id(doc_id);
        leave.setPatient_id(patient_id);
        leave.setStart_day(start_date);
        leave.setDays(days);
        leaveRepository.save(leave);
    }
}
