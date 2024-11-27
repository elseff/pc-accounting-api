package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.ComputerType;

@Repository
public interface ComputerTypeRepository extends JpaRepository<ComputerType, Long> {
    ComputerType findByCode(String code);
}
