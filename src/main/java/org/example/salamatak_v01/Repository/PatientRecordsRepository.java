package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.PatientRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRecordsRepository extends JpaRepository<PatientRecords, Integer>
{
    PatientRecords findPatientRecordsById(Integer id);

    @Query("select p from PatientRecords p where p.id = :user_id")
    PatientRecords findPatientRecordsByUserId(Integer user_id);
}
