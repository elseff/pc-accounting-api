package ru.elseff.pcaccounting.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.elseff.pcaccounting.dao.enums.EnumDeviceType;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "device_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    /**
     * @see EnumDeviceType
     */
    @Column(name = "code", unique = true, nullable = false)
    String code;

    @Column(name = "title", nullable = false)
    String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_group_id", nullable = false)
    DeviceGroup group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceType that)) return false;
        return getId().equals(that.getId())
               && getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode());
    }
}
