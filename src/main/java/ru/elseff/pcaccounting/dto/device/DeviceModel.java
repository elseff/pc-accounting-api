package ru.elseff.pcaccounting.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceModel {
    Long id;
    String title;
    TypeModel type;
    Integer price;
    String number;
    Long computerId;
}
