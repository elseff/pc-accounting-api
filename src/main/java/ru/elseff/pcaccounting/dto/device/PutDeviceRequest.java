package ru.elseff.pcaccounting.dto.device;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PutDeviceRequest {
    Long computerId;
    Long deviceId;
}
