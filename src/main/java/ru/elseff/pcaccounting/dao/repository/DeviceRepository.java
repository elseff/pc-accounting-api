package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByTypeCodeAndComputerIsNullAndDeletedIsFalse(String code);

    List<Device> findAllByTypeCodeAndDeletedIsFalse(String code);

    List<Device> findAllByTypeGroupCodeAndDeletedIsFalse(String group);

    List<Device> findAllByTypeGroupCodeAndComputerIsNullAndDeletedIsFalse(String group);

    List<Device> findAllByComputerIsNullAndDeletedIsFalse();

    List<Device> findAllByDeletedIsFalse();
}

