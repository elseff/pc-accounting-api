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
@Table(name = "computer_device_log")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerDeviceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    @JoinColumn(name = "computer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Computer computer;

    @JoinColumn(name = "device_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_operation_type_id")
    DeviceOperationType operationType;

    @Column(name = "message", nullable = false)
    String message;

    @Column(name = "date", nullable = false)
    LocalDateTime date;
}
