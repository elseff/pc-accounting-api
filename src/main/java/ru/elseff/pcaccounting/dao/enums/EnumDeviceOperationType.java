package ru.elseff.pcaccounting.dao.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnumDeviceOperationType {
    ADDED("added", "Устройство добавлено в компьютер"),
    REMOVED("removed", "Устройство удалено из компьютера");

    String code;
    String title;

    public static EnumDeviceOperationType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
