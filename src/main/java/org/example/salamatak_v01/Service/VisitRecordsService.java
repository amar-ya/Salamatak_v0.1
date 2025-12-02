package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiException;
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
        List<VisitRecords> vr =  visitRecordsRepository.findAll();
        if (vr.isEmpty()){
            throw new ApiException("No VisitRecords found");
        }
        return vr;
    }

    public void addVisitRecords(Integer doctor_id, VisitRecords VisitRecords){
        Doctors doc = doctorsRepository.findDoctorsById(doctor_id);
        if (doc == null){
            throw new ApiException("doctor not found");
        } else {
            visitRecordsRepository.save(VisitRecords);
        }
    }

    public void updateVisitRecords(Integer id, Integer doctor_id, VisitRecords VisitRecords){
        VisitRecords visit = visitRecordsRepository.findVisitRecordById(id);
        if (visit.getDoctor_id().equals(doctor_id)){
            visitRecordsRepository.save(VisitRecords);
        }else if(visit == null){
            throw new ApiException("visit not found");
        }else {
            throw new ApiException("this visit does not belong to the doctor");
        }
    }

    public void
    deleteVisitRecords(Integer id, Integer doc_id){
        VisitRecords visit = visitRecordsRepository.findVisitRecordById(id);
        if (visit == null){
            throw new ApiException("visit not found");
        }else if (visit.getDoctor_id().equals(doc_id)){
            visitRecordsRepository.delete(visit);
        }else {
            throw new ApiException("this visit does not belong to the doctor");
        }
    }
}
