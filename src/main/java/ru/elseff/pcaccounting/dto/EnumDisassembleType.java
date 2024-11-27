package ru.elseff.pcaccounting.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnumDisassembleType {
    TOTALLY("Компьютер %s списан полностью"),
    TO_WAREHOUSE("Компьютер %s разобран, детали доставлены на склад");

    String title;
}
