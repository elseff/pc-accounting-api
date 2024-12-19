package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "device")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "number", nullable = false, unique = true, updatable = false)
    String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type_id", nullable = false)
    DeviceType type;

    @Column(name = "price")
    Integer price;

    @Column(name = "deleted", nullable = false)
    boolean deleted;

    @JoinColumn(name = "computer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Computer computer;

    @PrePersist
    public void prePersist() {
        number = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device device)) return false;
        return getId().equals(device.getId())
               && getTitle().equals(device.getTitle())
               && getNumber().equals(device.getNumber())
               && getPrice().equals(device.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getNumber(), getPrice());
    }
}
