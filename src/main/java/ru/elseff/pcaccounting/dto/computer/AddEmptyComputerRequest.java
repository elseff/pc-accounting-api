package ru.elseff.pcaccounting.dto.computer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddEmptyComputerRequest {
    String title;
    String type;
}
