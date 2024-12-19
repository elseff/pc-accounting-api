package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Computer;
import ru.elseff.pcaccounting.dao.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByComputerIsNull();
}
