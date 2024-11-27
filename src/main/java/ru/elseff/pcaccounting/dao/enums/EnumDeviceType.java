package ru.elseff.pcaccounting.dao.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnumDeviceType {
    KEYBOARD("peripherals", "keyboard", "Клавиатура"),
    MOUSE("peripherals", "mouse", "Мышь"),
    PRINTER("peripherals", "printer", "Принтер"),
    DISPLAY("peripherals", "display", "Монитор"),
    SPEAKER("peripherals", "speaker", "Колонки"),
    HEADPHONES("peripherals", "headphones", "Наушники"),
    SCANNER("peripherals", "scanner", "Сканер"),
    CAMERA("peripherals", "camera", "Камера"),

    HARD_DRIVE("accessories", "hard-drive", "Жесткий диск"),
    RAM("accessories", "ram", "Оперативная память"),
    MOTHERBOARD("accessories", "motherboard", "Материнская плата"),
    VIDEO_CARD("accessories", "video-card", "Видеокарта"),
    CPU("accessories", "cpu", "Процессор"),
    DRIVE("accessories", "drive", "Дисковод"),
    AUDIO_CARD("accessories", "audio-card", "Звуковая карта"),
    POWER_UNIT("accessories", "power-unit", "Блок питания"),
    CASE("accessories", "case", "Корпус"),
    HDD("accessories", "hdd", "HDD"),
    SSD("accessories", "ssd", "SSD"),
    NETWORK_CARD("accessories", "network-card", "Сетевая карта"),
    COOLER("accessories", "cooler", "Кулер");

    String group;
    String code;
    String title;

    public static EnumDeviceType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
