package com.gaslink.api.modules.vendor;
import com.gaslink.api.shared.audit.AuditableEntity;
import com.gaslink.api.shared.enums.VerificationStatus;
import com.gaslink.api.shared.enums.VendorAccountStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name = "vendors")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Vendor extends AuditableEntity {
    @Id @Column(name = "id", columnDefinition = "uuid") private UUID id;
    @Column(name = "business_name", nullable = false) private String businessName;
    @Column(name = "business_address") private String businessAddress;
    @Column(name = "nin", nullable = false, unique = true) private String nin;
    private Double lat;
    private Double lng;
    @Column(name = "service_radius_km") private Double serviceRadiusKm;
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status") private VerificationStatus verificationStatus = VerificationStatus.PENDING;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status") private VendorAccountStatus accountStatus = VendorAccountStatus.ENABLED;
    @Column(name = "account_disabled_reason") private String accountDisabledReason;
    @Column(name = "subscription_status") private String subscriptionStatus = "INACTIVE";
    @Column(name = "is_open") private boolean isOpen = false;
    @Column(precision = 3, scale = 2) private BigDecimal rating = BigDecimal.ZERO;
    @Column(name = "total_reviews") private int totalReviews = 0;
}