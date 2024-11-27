package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "computer")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "number", nullable = false, unique = true, updatable = false)
    String number;

    @Column(name = "cabinet")
    Integer cabinet;

    @Column(name = "ready", nullable = false)
    boolean ready;

    @ManyToOne
    @JoinColumn(name = "computer_type_id", nullable = false)
    ComputerType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "computer")
    List<Device> devices;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "computer")
    Employee employee;

    @PrePersist
    public void prePersist() {
        number = UUID.randomUUID().toString();
        ready = false;
    }
}
