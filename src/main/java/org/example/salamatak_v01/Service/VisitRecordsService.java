package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.VisitRecords;
import org.example.salamatak_v01.Repository.DoctorsRepository;
import org.example.salamatak_v01.Repository.VisitRecordsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitRecordsService
{
    private final VisitRecordsRepository visitRecordsRepository;
    private final DoctorsRepository doctorsRepository;

    public List<VisitRecords> getAll(){
        return visitRecordsRepository.findAll();
    }

    public String addVisitRecords(Integer doctor_id, VisitRecords VisitRecords){
        Doctors doc = doctorsRepository.findDoctorsById(doctor_id);
        if (doc == null){
            return "doctor not found";
        } else {
            visitRecordsRepository.save(VisitRecords);
            return "success";
        }
    }

    public String updateVisitRecords(Integer id, Integer doctor_id, VisitRecords VisitRecords){
        VisitRecords visit = visitRecordsRepository.findVisitRecordById(id);
        if (visit.getDoctor_id().equals(doctor_id)){
            visitRecordsRepository.save(VisitRecords);
            return "success";
        }else if(visit == null){
            return "visit not found";
        }else {
            return "this visit does not belong to the doctor";
        }
    }

    public String deleteVisitRecords(Integer id, Integer doc_id){
        VisitRecords visit = visitRecordsRepository.findVisitRecordById(id);
        if (visit == null){
            return "visit not found";
        }else if (visit.getDoctor_id().equals(doc_id)){
            visitRecordsRepository.delete(visit);
            return "success";
        }else {
            return "this visit does not belong to the doctor";
        }
    }
}
