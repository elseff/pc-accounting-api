package ru.elseff.pcaccounting.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    Long id;

    @Column(name = "number", nullable = false)
    String number;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "document_data", columnDefinition = "BYTEA")
    byte[] documentData;

    @JoinColumn(name = "computer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Computer computer;

    @PrePersist
    public void prePersist() {
        number = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }
}
