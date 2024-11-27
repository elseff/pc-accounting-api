package ru.elseff.pcaccounting.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.elseff.pcaccounting.dto.computer.ComputerModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeModel {
    Long id;
    String firstName;
    String lastName;
    String patronymic;
    String speciality;
    ComputerModel computer;
}
