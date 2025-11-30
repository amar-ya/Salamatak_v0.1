package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.Times;
import org.example.salamatak_v01.Model.VisitRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;


@Repository
public interface TimesRepository extends JpaRepository<Times, Integer>
{
    @Query("select count(t) from Times t where t.doctor_id = :id")
    Integer findTimesCountByDoctor_id(Integer id);

    Times findTimesById(Integer id);

    @Query("select t from Times t where t.patient_id = null")
    List<Times> findAvaliableTimes();

    @Query("select t from Times t where t.doctor_id in (select d.id from Doctors d where d.speciality = :speciality) and t.patient_id is null")
    List<Times> findAvaliableTimesBySpeciality(String speciality);

    @Query("select t from Times t where t.time = :time")
    Times findTimeByTime(Time time);

    @Query("select d from Doctors d where d.id = :id")
    Doctors findDoctorByTime(Integer id);

    @Query("select v from VisitRecords v where v.doctor_id = :doc_id and v.patient_id = :id order by v.id desc limit 1")
    VisitRecords findVisitByDoctorAndPatient(Integer doc_id, Integer id);

}
