package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.ComputerDeviceLog;

@Repository
public interface ComputerDeviceLogRepository extends JpaRepository<ComputerDeviceLog, Long> {
}
