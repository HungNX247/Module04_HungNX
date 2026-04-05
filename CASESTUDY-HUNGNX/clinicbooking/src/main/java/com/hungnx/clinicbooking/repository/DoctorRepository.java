package com.hungnx.clinicbooking.repository;

import com.hungnx.clinicbooking.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query("""
select d\s
from Doctor d\s
join fetch d.specialty\s
order by d.id""")
    List<Doctor> findAllWithSpecialty();

    @Query("""
select d\s
from Doctor d\s
join fetch d.specialty\s
where d.id = :id""")
    Optional<Doctor> findByIdWithSpecialty(Integer id);
}
