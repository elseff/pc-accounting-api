package ru.elseff.pcaccounting.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.elseff.pcaccounting.dao.entity.Computer;
import ru.elseff.pcaccounting.dao.repository.ComputerRepository;
import ru.elseff.pcaccounting.dao.repository.ComputerTypeRepository;
import ru.elseff.pcaccounting.dto.computer.AddEmptyComputerRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerServiceTest {

    @SpyBean
    ComputerRepository computerRepository;
    @SpyBean
    ComputerTypeRepository computerTypeRepository;

    @Autowired
    ComputerService computerService;

    @Test
    @DisplayName("Добавление компьютера при неверном типе компьютера")
    void testWhenComputerTypeIncorrect_ShouldThrowException() {
        AddEmptyComputerRequest request = new AddEmptyComputerRequest();
        request.setTitle("new");
        request.setType("incorrect");

        assertAll(
                () -> assertThatThrownBy(() -> computerService.addComputer(request))
                        .describedAs("Ожидалось, что будет выброшено исключение")
                        .isInstanceOf(IllegalArgumentException.class)
                        .describedAs("Ожидалось, что исключение будет типа IllegalArgumentException")
                        .hasMessageContainingAll("Неверный тип компьютера: " + request.getType())
                        .describedAs("Ожидалось, что сообщение будет содержать Неверный тип компьютера")
        );

        verifyNoInteractions(computerRepository, computerTypeRepository);
    }

    @Test
    @DisplayName("Успешное добавление компьютера")
    void testAddComputer_ShouldAddComputerToDb() {
        AddEmptyComputerRequest request = new AddEmptyComputerRequest();
        request.setTitle("new");
        request.setType("laptop");


        assertAll(
                () -> assertDoesNotThrow(() -> computerService.addComputer(request))
        );

        Computer added = computerRepository.findByTitle("new");

        assertAll(
                () -> assertThat(added).isNotNull(),
                () -> assertThat(added.getTitle()).isEqualTo("new")
        );

        verify(computerRepository, times(1)).findByTitle(anyString());
        verify(computerTypeRepository, times(1)).findByCode(anyString());
        verify(computerRepository, times(1)).save(any(Computer.class));
        verifyNoMoreInteractions(computerRepository, computerTypeRepository);
    }
}
