package ru.elseff.pcaccounting.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.elseff.pcaccounting.dto.EnumDisassembleType;
import ru.elseff.pcaccounting.dto.computer.AddComputerResponse;
import ru.elseff.pcaccounting.dto.computer.AddEmptyComputerRequest;
import ru.elseff.pcaccounting.dto.computer.ComputerModel;
import ru.elseff.pcaccounting.dto.device.PutDeviceRequest;
import ru.elseff.pcaccounting.dto.device.PutDeviceResponse;
import ru.elseff.pcaccounting.dto.device.RemoveDeviceRequest;
import ru.elseff.pcaccounting.dto.device.RemoveDeviceResponse;
import ru.elseff.pcaccounting.generator.ModelGenerator;
import ru.elseff.pcaccounting.service.ComputerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/computers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Computer controller", description = "Управление компьютерами")
public class ComputerController {

    ComputerService computerService;
    ModelGenerator modelGenerator;

    @Operation(
            method = "POST",
            summary = "Добавить компьютер",
            description = "Добавить новый шаблон компьютера (устройства и комплектующие к нему можно будет добавить позже)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Компьютер успешно добавлен",
                            content = @Content(schema = @Schema(implementation = ComputerModel.class))
                    )
            }
    )
    @PostMapping
    public AddComputerResponse addComputer(@RequestBody @Valid AddEmptyComputerRequest request) {
        var builder = AddComputerResponse.builder();
        builder.computerTitle(request.getTitle());
        try {
            computerService.addComputer(request);
            builder.message("Успешно добавлен новый компьютер");
        } catch (Exception e) {
            builder.message("Не удалось добавить компьютер");
            log.error("Не удалось добавить компьютер: " + request.getTitle());
        }
        return builder.build();
    }

    @Operation(
            method = "GET",
            summary = "Все компьютеры",
            description = "Получить список всех компьютеров",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешно получен список компьютеров",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComputerModel.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "ready", description = "Только полностью собранные компьютеры")
            }
    )
    @GetMapping
    public List<ComputerModel> findAll(@RequestParam(required = false) Boolean ready) {
        return computerService.findAll()
                .stream()
                .map(modelGenerator::generateComputerModel)
                .collect(Collectors.toList());
    }


    @Operation(
            method = "POST",
            summary = "Добавить устройство",
            description = "Добавить в компьютер устройство",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Устройство успешно добавлено",
                            content = @Content(schema = @Schema(implementation = PutDeviceResponse.class))
                    )
            }
    )
    @PostMapping("/put_device")
    public PutDeviceResponse putDevice(@RequestBody @Valid PutDeviceRequest request) {
        var builder = PutDeviceResponse.builder();
        builder.deviceId(request.getDeviceId());
        builder.computerId(request.getComputerId());
        builder.date(LocalDateTime.now());
        try {
            computerService.putDevice(request, builder);

            String message = String.format("Устройство %s успешно добавлено к компьютер %s", request.getDeviceId(), request.getComputerId());
            builder.message(message);
            log.info(message);
        } catch (IllegalArgumentException e) {
            builder.message(e.getMessage());
            log.info(e.getMessage());
        }
        return builder.build();
    }

    @Operation(
            method = "POST",
            summary = "Удалить устройство",
            description = "Удалить устройство из компьютера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Устройство успешно удалено",
                            content = @Content(schema = @Schema(implementation = RemoveDeviceResponse.class))
                    )
            }
    )
    @PostMapping("/remove_device")
    public RemoveDeviceResponse removeDevice(@RequestBody @Valid RemoveDeviceRequest request) {
        var builder = RemoveDeviceResponse.builder();
        try {
            computerService.removeDevice(request.getComputerId(), request.getDeviceId(), builder);
            builder.message("Устройство удалено из компьютера");
        } catch (Exception e) {
            builder.message(e.getMessage());
            log.error(e.getMessage());
        }
        return builder.build();
    }

    @Operation(
            method = "POST",
            summary = "Разобрать компьютер",
            description = "Списание компьютера. 2 варианта - разобрать и доставить комплектующие и устройства на склад " +
                          "и полностью списать компьютер (устройства будут удалены вместе с компьютером",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Компьютер успешно списан",
                            content = @Content(schema = @Schema(description = "Статус", implementation = String.class))
                    )
            },
            parameters = {

            }
    )
    @PostMapping("/{computerId}/disassemble")
    public String disassemble(@PathVariable Long computerId, @RequestParam(defaultValue = "true") boolean toWarehouse) {
        EnumDisassembleType disassembleType = computerService.disassemble(computerId, toWarehouse);
        return String.format(disassembleType.getTitle(), computerId);
    }
}
