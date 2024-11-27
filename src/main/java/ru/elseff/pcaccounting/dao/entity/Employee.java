package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "patronymic")
    String patronymic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speciality_id", nullable = false)
    Speciality speciality;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id", referencedColumnName = "id")
    Computer computer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return getId().equals(employee.getId())
               && getFirstName().equals(employee.getFirstName())
               && getLastName().equals(employee.getLastName())
               && getPatronymic().equals(employee.getPatronymic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPatronymic());
    }
}
