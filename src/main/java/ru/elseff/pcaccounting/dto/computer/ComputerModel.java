package ru.elseff.pcaccounting.dto.computer;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.elseff.pcaccounting.dto.device.DeviceModel;
import ru.elseff.pcaccounting.dto.employee.EmployeeModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerModel {
    Long id;
    String number;
    String title;
    String type;
    boolean ready;
    Integer cabinet;
    EmployeeModel responsibleEmployee;
    List<DeviceModel> devices;
}
