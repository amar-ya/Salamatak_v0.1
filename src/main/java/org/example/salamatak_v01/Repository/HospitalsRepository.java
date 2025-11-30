package org.example.salamatak_v01.Repository;

import org.example.salamatak_v01.Model.Hospitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalsRepository extends JpaRepository<Hospitals, Integer>
{
    Hospitals findHospitalsById(Integer id);
}
