package ru.elseff.pcaccounting.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.elseff.pcaccounting.dto.computer.ComputerModel;
import ru.elseff.pcaccounting.dto.device.DeviceModel;
import ru.elseff.pcaccounting.dto.device.DeviceOperationTypeModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerDeviceLogModel {
    Long id;
    ComputerModel computer;
    DeviceModel device;
    DeviceOperationTypeModel operation;
    String message;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime date;
}
