package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.elseff.pcaccounting.dao.enums.EnumComputerType;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "computer_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    /**
     * @see EnumComputerType
     */
    @Column(name = "code", unique = true, nullable = false)
    String code;

    @Column(name = "title", nullable = false)
    String title;
}
