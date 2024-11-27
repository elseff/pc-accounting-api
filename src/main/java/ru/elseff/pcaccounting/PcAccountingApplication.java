package ru.elseff.pcaccounting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PcAccountingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PcAccountingApplication.class, args);
    }

}
