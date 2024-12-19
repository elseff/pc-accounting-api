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
    KEYBOARD(Constants.PERIPHERALS, "keyboard", "Клавиатура"),
    MOUSE(Constants.PERIPHERALS, "mouse", "Мышь"),
    PRINTER(Constants.PERIPHERALS, "printer", "Принтер"),
    DISPLAY(Constants.PERIPHERALS, "display", "Монитор"),
    SPEAKER(Constants.PERIPHERALS, "speaker", "Колонки"),
    HEADPHONES(Constants.PERIPHERALS, "headphones", "Наушники"),
    SCANNER(Constants.PERIPHERALS, "scanner", "Сканер"),
    CAMERA(Constants.PERIPHERALS, "camera", "Камера"),

    HARD_DRIVE(Constants.ACCESSORIES, "hard-drive", "Жесткий диск"),
    RAM(Constants.ACCESSORIES, "ram", "Оперативная память"),
    MOTHERBOARD(Constants.ACCESSORIES, "motherboard", "Материнская плата"),
    VIDEO_CARD(Constants.ACCESSORIES, "video-card", "Видеокарта"),
    CPU(Constants.ACCESSORIES, "cpu", "Процессор"),
    DRIVE(Constants.ACCESSORIES, "drive", "Дисковод"),
    AUDIO_CARD(Constants.ACCESSORIES, "audio-card", "Звуковая карта"),
    POWER_UNIT(Constants.ACCESSORIES, "power-unit", "Блок питания"),
    CASE(Constants.ACCESSORIES, "case", "Корпус"),
    HDD(Constants.ACCESSORIES, "hdd", "HDD"),
    SSD(Constants.ACCESSORIES, "ssd", "SSD"),
    NETWORK_CARD(Constants.ACCESSORIES, "network-card", "Сетевая карта"),
    COOLER(Constants.ACCESSORIES, "cooler", "Кулер");

    String group;
    String code;
    String title;

    public static EnumDeviceType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    private static class Constants {
        public static final String PERIPHERALS = "peripherals";
        public static final String ACCESSORIES = "accessories";
    }
}
