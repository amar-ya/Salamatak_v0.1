package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SickLeaveRepository extends JpaRepository<SickLeave, Integer>
{
    @Query("select l from SickLeave l where l.patient_id = :id")
    List<SickLeave> findSickLeavesByPatient_id(Integer id);

    @Query("select p from Patients p where p.id = (select l.patient_id from SickLeave l where l.id = :id)")
    Patients findPatientBySickLeaveId(Integer id);

    @Query("select d from Doctors d where d.id = (select l.doctor_id from SickLeave l where l.id = :id)")
    Doctors findDoctorBySickLeaveId(Integer id);

    @Query("select r from Times r where r.patient_id = (select l.patient_id from SickLeave l where l.id = :id)")
    public Times findVisitByPatientId(Integer id);
}
