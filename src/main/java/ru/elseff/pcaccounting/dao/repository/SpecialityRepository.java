package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    Speciality findByCode(String code);
}
