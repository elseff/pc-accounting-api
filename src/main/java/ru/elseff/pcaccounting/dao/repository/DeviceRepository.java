package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
//
//    @Query(value = FREE_DEVICES_BY_TYPE_CODE)
//    List<Device> findAllFreeDevicesByTypeCode(String type);
//
//    @Query(value = FREE_DEVICES_BY_GROUP_CODE)
//    List<Device> findAllFreeDevicesByTypeGroupCode(String group);
//
//    @Query(value = FREE_DEVICES)
//    List<Device> findAllFreeDevices();
//
//    @Query(value = ALL_DEVICES_BY_TYPE_CODE)
//    List<Device> findAllByType_Code(String code);
//
//    @Query(value = ALL_DEVICES_BY_GROUP_CODE)
//    List<Device> findAllByTypeGroup_Code(String code);

//    @NonNull
//    @Query(value = ALL_DEVICES)
//    List<Device> findAll();

    List<Device> findAllByComputerId(Long computerId);

    List<Device> findAllByTypeCodeAndComputerIsNull(String code);

    List<Device> findAllByTypeCode(String code);

    List<Device> findAllByTypeGroupCode(String group);

    List<Device> findAllByTypeGroupCodeAndComputerIsNull(String group);

    List<Device> findAllByComputerIsNull();
}

