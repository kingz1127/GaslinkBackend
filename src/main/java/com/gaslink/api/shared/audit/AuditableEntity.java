package com.gaslink.api.shared.audit;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@MappedSuperclass
@Getter @Setter
public abstract class AuditableEntity {
    @Column(name = "created_at", updatable = false) private Instant createdAt;
    @Column(name = "updated_at") private Instant updatedAt;

    @PrePersist protected void onCreate() { createdAt = updatedAt = Instant.now(); }
    @PreUpdate  protected void onUpdate()  { updatedAt = Instant.now(); }
}