package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.UsersKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersKeyRepository extends JpaRepository<UsersKeys, Integer>
{
    @Query("select k from UsersKeys k where k.patient_id = :id")
    public UsersKeys findByPatientId(Integer id);

    @Query("select k from UsersKeys k where k.doc_id = :id")
    public UsersKeys findByDoctorId(Integer id);

    @Query("select p from Patients p where p.id = (select k.patient_id from UsersKeys k where k.id = :id)")
    Patients findPatientByKeyId(Integer id);

    @Query("select p from Doctors p where p.id = (select k.patient_id from UsersKeys k where k.id = :id)")
    public Doctors findDoctorByKeyId(Integer id);


}
