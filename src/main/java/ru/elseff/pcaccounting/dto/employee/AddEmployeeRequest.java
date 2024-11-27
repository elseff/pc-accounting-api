package ru.elseff.pcaccounting.dto.employee;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddEmployeeRequest {
    String firstName;
    String lastName;
    String patronymic;
    String speciality;
}
