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
import ru.elseff.pcaccounting.dto.device.AddDeviceRequest;
import ru.elseff.pcaccounting.dto.device.AddDeviceResponse;
import ru.elseff.pcaccounting.dto.device.DeviceModel;
import ru.elseff.pcaccounting.generator.ModelGenerator;
import ru.elseff.pcaccounting.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Device controller", description = "Управление устройствами")
public class DeviceController {

    DeviceService deviceService;
    ModelGenerator modelGenerator;

    @Operation(
            method = "POST",
            summary = "Добавить устройство",
            description = "Добавить новое устройство",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Устройство успешно добавлено",
                            content = @Content(schema = @Schema(implementation = AddDeviceResponse.class))
                    )
            }
    )
    @PostMapping
    public AddDeviceResponse addDevice(@RequestBody @Valid AddDeviceRequest request) {
        var builder = AddDeviceResponse.builder();
        builder.deviceTitle(request.getTitle());
        try {
            deviceService.addDevice(request);
            builder.message("Устройство успешно добавлено");
        } catch (Exception e) {
            builder.message("Не удалось сохранить устройство");
            log.error("Не удалось сохранить устройство " + request.getTitle());
        }

        return builder.build();
    }

    @Operation(
            method = "GET",
            summary = "Все устройства",
            description = "Получить список всех устройств",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все устройства успешно получены",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeviceModel.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "code", description = "Код устройства"),
                    @Parameter(name = "group", description = "Группа устройств"),
                    @Parameter(name = "free", description = "Только свободные устройства")
            }
    )
    @GetMapping
    public List<DeviceModel> findAll(@RequestParam(required = false) String code,
                                     @RequestParam(required = false) String group,
                                     @RequestParam(required = false, defaultValue = "false") boolean free) {
        return deviceService.findAll(code, group, free)
                .stream()
                .map(modelGenerator::generateDeviceModel)
                .collect(Collectors.toList());
    }

}
