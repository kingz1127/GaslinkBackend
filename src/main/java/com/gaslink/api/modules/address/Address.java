package com.gaslink.api.modules.address;
import com.gaslink.api.shared.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "addresses")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    private String label;
    @Column(name = "address_line", nullable = false) private String addressLine;
    private String city;
    private String state;
    private Double lat;
    private Double lng;
    @Column(name = "is_default") private boolean isDefault = false;
}