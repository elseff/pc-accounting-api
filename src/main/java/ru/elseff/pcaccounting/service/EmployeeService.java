package ru.elseff.pcaccounting.service;

import com.itextpdf.text.DocumentException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elseff.pcaccounting.dao.entity.*;
import ru.elseff.pcaccounting.dao.enums.EnumEmployeeOperationType;
import ru.elseff.pcaccounting.dao.enums.EnumSpeciality;
import ru.elseff.pcaccounting.dao.repository.*;
import ru.elseff.pcaccounting.dto.PassResponsibilityResponse;
import ru.elseff.pcaccounting.dto.employee.AddEmployeeRequest;
import ru.elseff.pcaccounting.dto.employee.PinComputerResponse;
import ru.elseff.pcaccounting.generator.ModelGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {

    PdfService pdfService;
    DocumentRepository documentRepository;
    ModelGenerator modelGenerator;
    ComputerService computerService;
    EmployeeRepository employeeRepository;
    SpecialityRepository specialityRepository;
    ComputerEmployeeLogRepository computerEmployeeLogRepository;
    EmployeeOperationTypeRepository employeeOperationTypeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void addEmployee(AddEmployeeRequest request) {
        EnumSpeciality enumSpeciality = EnumSpeciality.of(request.getSpeciality());
        if (enumSpeciality == null) {
            String message = "Неверная должность: " + request.getSpeciality();
            log.error(message);
            throw new IllegalArgumentException(message);
        }

        Speciality speciality = specialityRepository.findByCode(enumSpeciality.getCode());
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .patronymic(request.getPatronymic())
                .speciality(speciality)
                .build();
        employee = employeeRepository.save(employee);
        log.info("Добавлен новый сотрудник: " + employee.getFirstName() + " " + employee.getLastName());
    }

    public void pinComputerByEmployee(Long employeeId, Long computerId, PinComputerResponse.PinComputerResponseBuilder builder) {
        Employee employee = findById(employeeId);
        if (employee.getComputer() != null) {
            throw new IllegalArgumentException("За сотрудником уже закреплён компьютер");
        }
        Computer computer = computerService.findById(computerId);
        if (computer.getEmployee() != null) {
            throw new IllegalArgumentException("Этот компьютер уже закреплён за другим сотрудником");
        }

        employee.setComputer(computer);
        employeeRepository.save(employee);
        createComputerEmployeeLog(computer, employee, EnumEmployeeOperationType.PINNED);
        builder.computerTitle(computer.getTitle());
        builder.employeeName(employee.getFirstName());
    }

    public void unpinComputerByEmployee(Long employeeId, PinComputerResponse.PinComputerResponseBuilder builder) {
        Employee employee = findById(employeeId);
        if (employee.getComputer() == null) {
            throw new IllegalArgumentException("За сотрудником не закреплён компьютер");
        }
        Computer computer = employee.getComputer();
        employee.setComputer(null);
        employeeRepository.save(employee);
        createComputerEmployeeLog(computer, employee, EnumEmployeeOperationType.UNPINNED);
        builder.computerId(computer.getId());
        builder.computerTitle(computer.getTitle());
        builder.employeeName(employee.getFirstName());
    }

    public Employee findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Сотрудник с id " + employeeId + " не найден"));
    }

    public String passResponsibility(Long from, Long to, PassResponsibilityResponse.PassResponsibilityResponseBuilder builder) throws DocumentException, IOException {
        Employee fromEmployee = findById(from);
        if (fromEmployee.getComputer() == null) {
            throw new IllegalArgumentException("За первым сотрудником не закреплён компьютер");
        }
        Employee toEmployee = findById(to);
        if (toEmployee.getComputer() != null) {
            throw new IllegalArgumentException("За вторым сотрудником уже закреплён компьютер");
        }

        Computer computer = fromEmployee.getComputer();
        fromEmployee.setComputer(null);
        toEmployee.setComputer(computer);
        employeeRepository.save(fromEmployee);
        employeeRepository.save(toEmployee);

        String message = "Передана ответственность за компьютер " + computer.getTitle() + " от " + fromEmployee.getFirstName() + " к " + toEmployee.getFirstName();
        byte[] data = pdfService.generatePassResponsibilityDocument(message);

        Document document = Document.builder()
                .documentData(data)
                .computer(computer)
                .description(message)
                .build();
        document = documentRepository.save(document);

        createComputerEmployeeLog(computer, fromEmployee, EnumEmployeeOperationType.UNPINNED);
        createComputerEmployeeLog(computer, toEmployee, EnumEmployeeOperationType.PINNED);
        log.info(message);
        builder.computer(modelGenerator.generateShortComputerModel(computer));
        builder.from(modelGenerator.generateShortEmployeeModel(fromEmployee));
        builder.to(modelGenerator.generateShortEmployeeModel(toEmployee));
        builder.date(LocalDateTime.now());
        builder.message(message);

        return document.getNumber();
    }

    public void createComputerEmployeeLog(Computer computer, Employee employee, EnumEmployeeOperationType type) {
        EmployeeOperationType employeeOperationType = employeeOperationTypeRepository.findByCode(type.getCode());

        ComputerEmployeeLog computerEmployeeLog = ComputerEmployeeLog.builder()
                .computer(computer)
                .employee(employee)
                .operationType(employeeOperationType)
                .date(LocalDateTime.now())
                .message(type.getTitle())
                .build();

        computerEmployeeLogRepository.save(computerEmployeeLog);
        if (EnumEmployeeOperationType.PINNED.equals(type)) {
            log.info("Компьютер {} закреплен за сотрудником {}", computer.getTitle(), employee.getFirstName());
        } else {
            log.info("Компьютер {} откреплён от сотрудника {}", computer.getTitle(), employee.getFirstName());
        }
    }
}
