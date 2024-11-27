package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByTypeCodeAndComputerIsNull(String code);

    List<Device> findAllByTypeCode(String code);

    List<Device> findAllByTypeGroupCode(String group);

    List<Device> findAllByTypeGroupCodeAndComputerIsNull(String group);

    List<Device> findAllByComputerIsNull();
}

