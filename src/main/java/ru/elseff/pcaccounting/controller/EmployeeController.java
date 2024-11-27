package ru.elseff.pcaccounting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.elseff.pcaccounting.dto.PassResponsibilityResponse;
import ru.elseff.pcaccounting.dto.employee.AddEmployeeRequest;
import ru.elseff.pcaccounting.dto.employee.AddEmployeeResponse;
import ru.elseff.pcaccounting.dto.employee.EmployeeModel;
import ru.elseff.pcaccounting.dto.employee.PinComputerResponse;
import ru.elseff.pcaccounting.generator.ModelGenerator;
import ru.elseff.pcaccounting.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Employee controller", description = "Управление сотрудниками")
public class EmployeeController {

    EmployeeService employeeService;
    ModelGenerator modelGenerator;

    @Operation(
            method = "GET",
            summary = "Все сотрудники",
            description = "Посмотреть всех сотрудников",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешно получен список сотрудников",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeModel.class)))
                    )
            }
    )
    @GetMapping
    public List<EmployeeModel> findAll() {
        return employeeService.findAll()
                .stream()
                .map(modelGenerator::generateEmployeeModel)
                .collect(Collectors.toList());
    }

    @Operation(
            method = "POST",
            summary = "Добавить сотрудника",
            description = "Добавить нового сотрудника",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник успешно добавлен",
                            content = @Content(schema = @Schema(implementation = AddEmployeeResponse.class))
                    )
            }

    )
    @PostMapping
    public AddEmployeeResponse addEmployee(@RequestBody @Valid AddEmployeeRequest request) {
        var builder = AddEmployeeResponse.builder();
        builder.firstName(request.getFirstName());
        builder.lastName(request.getLastName());
        try {
            employeeService.addEmployee(request);
            builder.message("Добавлен новый сотрудник");
        } catch (Exception e) {
            log.error(e.getMessage());
            builder.message("Не удалось добавить сотрудника - " + e.getMessage());
        }
        return builder.build();
    }

    @Operation(
            method = "POST",
            summary = "Закрепление за сотрудником компьютера",
            description = "Закрепить за компьютером ответственного (сотрудника)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник успешно закреплён",
                            content = @Content(schema = @Schema(implementation = PinComputerResponse.class))
                    )
            }

    )
    @PostMapping("/{employeeId}/pin")
    public PinComputerResponse pinComputer(@PathVariable Long employeeId, @RequestParam Long computerId) {
        var builder = PinComputerResponse.builder();
        builder.computerId(computerId);
        builder.employeeId(employeeId);
        try {
            employeeService.pinComputerByEmployee(employeeId, computerId, builder);
            builder.message("Компьютер закреплен за сотрудником");
        } catch (Exception e) {
            log.error(e.getMessage());
            builder.message(e.getMessage());
        }
        return builder.build();
    }

    @Operation(
            method = "POST",
            summary = "Открепление от компьютера сотрудника",
            description = "Открепление ответственного(сотрудника) от компьютера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник успешно откреплён",
                            content = @Content(schema = @Schema(implementation = PinComputerResponse.class))
                    )
            }

    )
    @PostMapping("/{employeeId}/unpin")
    public PinComputerResponse unpinComputer(@PathVariable Long employeeId) {
        var builder = PinComputerResponse.builder();
        builder.employeeId(employeeId);
        try {
            employeeService.unpinComputerByEmployee(employeeId, builder);
            builder.message("Компьютер откреплён от сотрудника");
        } catch (Exception e) {
            log.error(e.getMessage());
            builder.message(e.getMessage());
        }
        return builder.build();
    }

    @Operation(
            method = "POST",
            summary = "Передача ответственности",
            description = "Передача ответственности за компьютер другому сотруднику",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ответственность за компьютер успешно передана другому сотруднику",
                            content = @Content(schema = @Schema(implementation = PassResponsibilityResponse.class))
                    )
            }

    )
    @PostMapping(value = "/pass")
    public PassResponsibilityResponse passResponsibility(@RequestParam Long from, @RequestParam Long to) {
        var builder = PassResponsibilityResponse.builder();
        try {
            String documentNumber = employeeService.passResponsibility(from, to, builder);
            builder.documentNumber(documentNumber);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return builder.build();
    }
}
