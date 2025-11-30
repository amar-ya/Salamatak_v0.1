package org.example.salamatak_v01.Repository;


import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.VisitRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Integer>
{

    Doctors findDoctorsById(Integer id);

    @Query("select v from VisitRecords v where v.doctor_id = :doc_id and v.patient_id = :patient_id order by v.id desc limit 1")
    VisitRecords findVisitRecordByDoctorAndPatientId(Integer doc_id, Integer patient_id);

    @Query("select p from Patients p where p.id = :id")
    Patients findPatientById(Integer id);
}
