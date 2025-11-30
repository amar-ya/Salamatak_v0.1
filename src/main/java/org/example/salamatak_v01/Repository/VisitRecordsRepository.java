package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.VisitRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRecordsRepository extends JpaRepository<VisitRecords, Integer>
{
    VisitRecords findVisitRecordById(Integer id);

    @Query("select v from VisitRecords v where v.time_id = :id")
    VisitRecords findByTimeId(Integer id);

    @Query("select p from Patients p where p.id = (select v.patient_id from VisitRecords v where v.id = :id)")
    Patients findPatientByVisitRecordId(Integer id);

    @Query("select v from VisitRecords v where v.patient_id = (select t.patient_id from Times t where t.id = :id)")
    VisitRecords findVisitRecordByPatientId(Integer id);


}
