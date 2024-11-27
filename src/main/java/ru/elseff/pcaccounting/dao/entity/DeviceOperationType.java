package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "device_operation_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceOperationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    @Column(name = "code", unique = true, nullable = false)
    String code;

    @Column(name = "title", nullable = false)
    String title;
}
