package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.EmployeeOperationType;

@Repository
public interface EmployeeOperationTypeRepository extends JpaRepository<EmployeeOperationType, Long> {
    EmployeeOperationType findByCode(String code);
}
