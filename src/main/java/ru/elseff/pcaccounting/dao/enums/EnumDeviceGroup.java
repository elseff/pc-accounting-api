package ru.elseff.pcaccounting.dao.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnumDeviceGroup {
    PERIPHERALS("peripherals", "Периферия"),
    ACCESSORIES("accessories", "Аксессуары");

    String code;
    String title;

    public static EnumDeviceGroup of(String code) {
        return Arrays.stream(values())
                .filter(group -> group.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
