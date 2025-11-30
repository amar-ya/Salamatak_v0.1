package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.Times;
import org.example.salamatak_v01.Model.UsersKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer>
{
    Patients findPatientById(Integer id);

    @Query("select p from Patients p where p.national_id = :national_id")
    Patients findPatientByNationalId(String national_id);

    @Query("select k from UsersKeys k where k.patient_id = :id ")
    UsersKeys findOTPByPatientId(Integer id);

    @Query("select k from UsersKeys k where k.patient_id = :id")
    UsersKeys findKeyByPatientId(Integer id);

    @Query("select p from Patients p where p.id = (select k.patient_id from UsersKeys k where k.login_key = :key)")
    Patients findPatientByLoginKey(String key);

    @Query("select t from Times t where t.patient_id = (select p.id from Patients p where p.id = :id) order by t.id desc limit 1")
    Times findTimeByPatientId(Integer id);

    @Query("select d from Doctors d where d.id = :id")
    Doctors findDoctorBySessionsDoc_Id(Integer id);

}
