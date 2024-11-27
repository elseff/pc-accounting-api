package ru.elseff.pcaccounting.dao.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnumSpeciality {
    SYSTEM_ADMIN("system-admin", "Системный администратор"),
    ACCOUNTANT("accountant", "Бухгалтер"),
    DEVELOPER("developer", "Программист"),
    ANALYST("analyst", "Аналитик"),
    TESTER("tester", "Тестировщик"),
    DIRECTOR("director", "Директор");

    String code;
    String title;

    public static EnumSpeciality of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
