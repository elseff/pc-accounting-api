package ru.elseff.pcaccounting.dao.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnumComputerType {
    PC("pc", "Персональный компьютер"),
    MONO("mono", "Моноблок"),
    MINI("mini", "Мини ПК"),
    LAPTOP("laptop", "Ноутбук");

    String code;
    String title;

    public static EnumComputerType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
