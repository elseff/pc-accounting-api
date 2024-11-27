package ru.elseff.pcaccounting.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.elseff.pcaccounting.dao.entity.Device;
import ru.elseff.pcaccounting.dao.entity.DeviceType;
import ru.elseff.pcaccounting.dao.enums.EnumDeviceType;
import ru.elseff.pcaccounting.dao.repository.DeviceRepository;
import ru.elseff.pcaccounting.dao.repository.DeviceTypeRepository;
import ru.elseff.pcaccounting.dto.device.AddDeviceRequest;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceService {

    DeviceRepository deviceRepository;
    DeviceTypeRepository deviceTypeRepository;

    public void addDevice(AddDeviceRequest request) {
        EnumDeviceType deviceType = EnumDeviceType.of(request.getType());
        if (deviceType == null) {
            String message = "Неверный тип устройства: " + request.getType();
            log.info(message);
            throw new IllegalArgumentException(message);
        }
        DeviceType type = deviceTypeRepository.findByCode(deviceType.getCode());
        Device device = Device.builder()
                .title(request.getTitle())
                .price(request.getPrice())
                .type(type)
                .build();
        deviceRepository.save(device);
        log.info("added new device {}", device.getTitle());
    }

    public List<Device> findAll(String code, String group, boolean free) {
        List<Device> devices;
        if (StringUtils.hasText(code)) {
            if (free) {
                devices = deviceRepository.findAllByTypeCodeAndComputerIsNull(code);
            } else {
                devices = deviceRepository.findAllByTypeCode(code);
            }
        } else if (StringUtils.hasText(group)) {
            if (free) {
                devices = deviceRepository.findAllByTypeGroupCodeAndComputerIsNull(group);
            } else {
                devices = deviceRepository.findAllByTypeGroupCode(group);
            }
        } else {
            if (free) {
                devices = deviceRepository.findAllByComputerIsNull();
            } else {
                devices = deviceRepository.findAll();
            }
        }

        return devices;
    }

    public Device findById(Long deviceId) throws IllegalArgumentException{
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Нет такого устройства: " + deviceId));
    }
}
