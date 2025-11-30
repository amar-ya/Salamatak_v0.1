package org.example.salamatak_v01.Repository;


import org.example.salamatak_v01.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>
{
    Admin findAdminById(Integer id);
}
