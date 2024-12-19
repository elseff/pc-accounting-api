package ru.elseff.pcaccounting.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.elseff.pcaccounting.dao.entity.*;
import ru.elseff.pcaccounting.dao.enums.EnumComputerType;
import ru.elseff.pcaccounting.dao.enums.EnumDeviceOperationType;
import ru.elseff.pcaccounting.dao.repository.*;
import ru.elseff.pcaccounting.dto.EnumDisassembleType;
import ru.elseff.pcaccounting.dto.computer.AddEmptyComputerRequest;
import ru.elseff.pcaccounting.dto.device.PutDeviceRequest;
import ru.elseff.pcaccounting.dto.device.PutDeviceResponse;
import ru.elseff.pcaccounting.dto.device.RemoveDeviceResponse;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComputerService {

    ComputerTypeRepository computerTypeRepository;
    ComputerRepository computerRepository;
    DeviceService deviceService;
    DeviceRepository deviceRepository;
    DeviceOperationTypeRepository deviceOperationTypeRepository;
    ComputerDeviceLogRepository computerDeviceLogRepository;

    public void addComputer(AddEmptyComputerRequest request) {
        EnumComputerType type = EnumComputerType.of(request.getType());
        if (type == null) {
            String message = "Неверный тип компьютера: " + request.getType();
            log.error(message);
            throw new IllegalArgumentException(message);
        }

        ComputerType computerType = computerTypeRepository.findByCode(type.getCode());

        Computer computer = Computer.builder()
                .title(request.getTitle())
                .type(computerType)
                .build();

        computerRepository.save(computer);
        log.info("Added new computer: " + computer.getTitle());
    }

    public List<Computer> findAll() {
        return computerRepository.findAllByDeletedIsFalse();
    }

    public List<Computer> findAllFree() {
        return computerRepository.findAllByDeletedIsFalseAndEmployeeIsNull();
    }

    public Computer findById(Long computerId) {
        return computerRepository.findById(computerId)
                .orElseThrow(() -> new IllegalArgumentException("Нет такого компьютера: " + computerId));
    }

    public void putDevice(PutDeviceRequest request, PutDeviceResponse.PutDeviceResponseBuilder builder) throws IllegalArgumentException {
        Device device = deviceService.findById(request.getDeviceId());
        if (device.getComputer() != null) {
            throw new IllegalArgumentException("Это устройство уже установлено в другом компьютере");
        }
        Computer computer = findById(request.getComputerId());

        device.setComputer(computer);

        deviceRepository.save(device);

        createComputerDeviceLog(device, computer, EnumDeviceOperationType.ADDED);

        builder.deviceTitle(device.getTitle());
        builder.computerTitle(computer.getTitle());
    }

    public void removeDevice(long computerId, long deviceId, RemoveDeviceResponse.RemoveDeviceResponseBuilder builder) throws IllegalArgumentException {
        Computer computer = findById(computerId);
        Device device = deviceService.findById(deviceId);
        List<Device> devices = computer.getDevices();
        boolean contains = devices.stream().anyMatch(dev -> dev.getId().equals(deviceId));
        if (!contains) {
            throw new IllegalArgumentException("На данном компьютере не установлено данное устройство");
        }

        device.setComputer(null);
        deviceRepository.save(device);
        log.info("Устройство {} удалено из компьютера {}", device.getTitle(), computer.getTitle());
        createComputerDeviceLog(device, computer, EnumDeviceOperationType.REMOVED);

        builder.computer(computer.getTitle());
        builder.device(device.getTitle());
    }

    private void createComputerDeviceLog(Device device, Computer computer, EnumDeviceOperationType type) {
        DeviceOperationType operationType = deviceOperationTypeRepository
                .findByCode(type.getCode());

        ComputerDeviceLog log = ComputerDeviceLog.builder()
                .computer(computer)
                .device(device)
                .message(type.getTitle())
                .operationType(operationType)
                .date(LocalDateTime.now())
                .build();

        computerDeviceLogRepository.save(log);
    }

    public EnumDisassembleType disassemble(Long computerId, boolean toWarehouse) {
        Computer computer = findById(computerId);
        EnumDisassembleType disassembleType = toWarehouse ? EnumDisassembleType.TO_WAREHOUSE : EnumDisassembleType.TOTALLY;
        List<Device> devices = computer.getDevices();
        log.error(String.valueOf(toWarehouse));
        log.error(disassembleType.getTitle());
        if (EnumDisassembleType.TO_WAREHOUSE.equals(disassembleType)) {
            for (Device device : devices) {
                device.setComputer(null);
                createComputerDeviceLog(device, computer, EnumDeviceOperationType.REMOVED);
            }
            deviceRepository.saveAll(devices);
            log.info("Компьютер {} разобран по запчастям", computer.getTitle());
        } else {
            for (Device device : devices) {
                device.setDeleted(true);
            }
            computer.setDeleted(true);
            computer.setEmployee(null);
            deviceRepository.saveAll(devices);
            computerRepository.save(computer);
            log.info("Компьютер {} списан полностью", computer.getTitle());
        }

        return disassembleType;
    }
}
