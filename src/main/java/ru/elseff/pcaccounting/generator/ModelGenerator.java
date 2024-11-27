package ru.elseff.pcaccounting.generator;

import org.springframework.stereotype.Component;
import ru.elseff.pcaccounting.dao.entity.*;
import ru.elseff.pcaccounting.dto.DocumentModel;
import ru.elseff.pcaccounting.dto.log.ComputerDeviceLogModel;
import ru.elseff.pcaccounting.dto.log.ComputerEmployeeLogModel;
import ru.elseff.pcaccounting.dto.computer.ComputerModel;
import ru.elseff.pcaccounting.dto.device.DeviceModel;
import ru.elseff.pcaccounting.dto.device.DeviceOperationTypeModel;
import ru.elseff.pcaccounting.dto.device.TypeModel;
import ru.elseff.pcaccounting.dto.employee.EmployeeModel;
import ru.elseff.pcaccounting.dto.employee.EmployeeOperationTypeModel;

import java.util.stream.Collectors;

@Component
public class ModelGenerator {

    public DeviceModel generateDeviceModel(Device device) {
        if (device == null) return null;

        return DeviceModel.builder()
                .id(device.getId())
                .title(device.getTitle())
                .number(device.getNumber())
                .price(device.getPrice())
                .type(this.generateTypeModel(device.getType()))
                .computerId(device.getComputer() != null ? device.getComputer().getId() : null)
                .build();
    }


    public DeviceModel generateShortDeviceModel(Device device) {
        if (device == null) return null;

        return DeviceModel.builder()
                .title(device.getTitle())
                .type(this.generateTypeModel(device.getType()))
                .build();
    }

    public TypeModel generateTypeModel(DeviceType type) {
        if (type == null) return null;

        return TypeModel.builder()
                .code(type.getCode())
                .group(type.getGroup().getCode())
                .build();
    }

    public ComputerModel generateComputerModel(Computer computer) {
        if (computer == null) return null;

        return ComputerModel.builder()
                .id(computer.getId())
                .number(computer.getNumber())
                .title(computer.getTitle())
                .ready(computer.isReady())
                .cabinet(computer.getCabinet())
                .responsibleEmployee(this.generateShortEmployeeModel(computer.getEmployee()))
                .devices(computer.getDevices().stream()
                        .map(this::generateShortDeviceModel)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public EmployeeModel generateEmployeeModel(Employee employee) {
        if (employee == null) return null;

        return EmployeeModel.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .patronymic(employee.getPatronymic())
                .speciality(employee.getSpeciality().getTitle())
                .computer(this.generateShortComputerModel(employee.getComputer()))
                .build();
    }

    public ComputerModel generateShortComputerModel(Computer computer) {
        if (computer == null) return null;

        return ComputerModel.builder()
                .id(computer.getId())
                .title(computer.getTitle())
                .number(computer.getNumber())
                .build();
    }

    public EmployeeModel generateShortEmployeeModel(Employee employee) {
        if (employee == null) return null;

        return EmployeeModel.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .speciality(employee.getSpeciality().getTitle())
                .build();
    }

    public ComputerDeviceLogModel generateComputerDeviceLogModel(ComputerDeviceLog log) {
        return ComputerDeviceLogModel.builder()
                .id(log.getId())
                .operation(this.generateDeviceOperationTypeModel(log.getOperationType()))
                .computer(this.generateShortComputerModel(log.getComputer()))
                .device(this.generateShortDeviceModel(log.getDevice()))
                .message(log.getMessage())
                .date(log.getDate())
                .build();
    }

    public DeviceOperationTypeModel generateDeviceOperationTypeModel(DeviceOperationType type) {
        return DeviceOperationTypeModel.builder()
                .code(type.getCode())
                .title(type.getTitle())
                .build();
    }

    public ComputerEmployeeLogModel generateComputerEmployeeLogModel(ComputerEmployeeLog log) {
        return ComputerEmployeeLogModel.builder()
                .id(log.getId())
                .operation(this.generateEmployeeOperationTypeModel(log.getOperationType()))
                .computer(this.generateShortComputerModel(log.getComputer()))
                .employee(this.generateShortEmployeeModel(log.getEmployee()))
                .message(log.getMessage())
                .date(log.getDate())
                .build();
    }

    public EmployeeOperationTypeModel generateEmployeeOperationTypeModel(EmployeeOperationType type) {
        return EmployeeOperationTypeModel.builder()
                .code(type.getCode())
                .title(type.getTitle())
                .build();
    }

    public DocumentModel generateDocumentModel(Document document) {
        return DocumentModel.builder()
                .id(document.getId())
                .createdAt(document.getCreatedAt())
                .description(document.getDescription())
                .number(document.getNumber())
                .build();
    }
}
