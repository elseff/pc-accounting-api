package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "computer_employee_log")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerEmployeeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    @JoinColumn(name = "computer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Computer computer;

    @JoinColumn(name = "employee_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_operation_type_id")
    EmployeeOperationType operationType;

    @Column(name = "message", nullable = false)
    String message;

    @Column(name = "date", nullable = false)
    LocalDateTime date;
}
