package ru.elseff.pcaccounting.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PinComputerResponse {
    Long computerId;
    String computerTitle;
    Long employeeId;
    String employeeName;
    String message;
}
