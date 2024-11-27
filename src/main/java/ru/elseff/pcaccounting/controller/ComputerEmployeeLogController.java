package ru.elseff.pcaccounting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elseff.pcaccounting.dao.repository.ComputerEmployeeLogRepository;
import ru.elseff.pcaccounting.dto.computer.ComputerModel;
import ru.elseff.pcaccounting.dto.log.ComputerEmployeeLogModel;
import ru.elseff.pcaccounting.generator.ModelGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees/logs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "ComputerEmployeeLog controller", description = "Логи сотрудников")
public class ComputerEmployeeLogController {
    ComputerEmployeeLogRepository computerEmployeeLogRepository;
    ModelGenerator modelGenerator;

    @Operation(
            method = "GET",
            summary = "Все логи сотрудников",
            description = "Получить список всех логов сотрудников",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Логи успешно получены",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComputerEmployeeLogModel.class)))
                    )
            }
    )
    @GetMapping
    public List<ComputerEmployeeLogModel> findAll() {
        return computerEmployeeLogRepository.findAll()
                .stream()
                .map(modelGenerator::generateComputerEmployeeLogModel)
                .collect(Collectors.toList());
    }
}
