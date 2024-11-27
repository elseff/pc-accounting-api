package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.elseff.pcaccounting.dao.enums.EnumSpeciality;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "speciality")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    /**
     * @see EnumSpeciality
     */
    @Column(name = "code", unique = true, nullable = false)
    String code;

    @Column(name = "title", nullable = false)
    String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speciality that)) return false;
        return getId().equals(that.getId())
               && getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode());
    }
}
