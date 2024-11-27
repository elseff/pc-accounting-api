package ru.elseff.pcaccounting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.elseff.pcaccounting.dto.computer.ComputerModel;
import ru.elseff.pcaccounting.dto.employee.EmployeeModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassResponsibilityResponse {
    EmployeeModel from;
    EmployeeModel to;
    ComputerModel computer;
    LocalDateTime date;
    String message;
    String documentNumber;
}
