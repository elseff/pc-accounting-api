package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    Computer findByTitle(String title);
}
