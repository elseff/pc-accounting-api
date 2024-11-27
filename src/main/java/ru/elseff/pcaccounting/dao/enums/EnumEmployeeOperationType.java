package ru.elseff.pcaccounting.dao.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnumEmployeeOperationType {
    PINNED("pinned", "За сотрудником закреплен компьютер"),
    UNPINNED("unpinned", "Компьютер откреплен от сотрудника");

    String code;
    String title;

    public static EnumEmployeeOperationType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
