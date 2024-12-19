package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Computer;

import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    List<Computer> findAllByDeletedIsFalse();
    Computer findByTitleAndDeletedIsFalse(String title);
    List<Computer> findAllByDeletedIsFalseAndEmployeeIsNull();
}
