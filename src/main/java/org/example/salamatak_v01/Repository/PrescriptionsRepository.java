package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.Prescriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionsRepository extends JpaRepository<Prescriptions, Integer>
{
    @Query("select d from Doctors d where d.id = :id")
    Doctors findDoctorById(Integer id);

    @Query("select p from Patients p where p.id = :id")
    Patients findPatientById(Integer id);

    @Query("select p from Prescriptions p where p.id = :id")
    Prescriptions findPrescriptionById(Integer id);

    @Query("select p from Prescriptions p where p.patient_Id = (select k.patient_id from UsersKeys k where k.login_key = :key)")
    List<Prescriptions> findPrescriptionByKey(String key);
}
