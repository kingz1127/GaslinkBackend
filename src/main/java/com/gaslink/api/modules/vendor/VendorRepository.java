package com.gaslink.api.modules.vendor;
import com.gaslink.api.shared.enums.VerificationStatus;
import com.gaslink.api.shared.enums.VendorAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {
    boolean existsByNin(String nin);
    List<Vendor> findByVerificationStatus(VerificationStatus status);
    List<Vendor> findByAccountStatus(VendorAccountStatus status);

    @Query(value = "SELECT v.id, v.business_name, v.business_address, v.nin, v.lat, v.lng, v.service_radius_km, " +
        "v.verification_status, v.account_status, v.subscription_status, v.rating, v.total_reviews, v.is_open, " +
        "u.full_name, u.phone, u.email, " +
        "(6371 * acos(cos(radians(:lat)) * cos(radians(v.lat)) * cos(radians(v.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(v.lat)))) AS distance_km " +
        "FROM vendors v JOIN users u ON u.id = v.id " +
        "WHERE v.verification_status = 'VERIFIED' AND v.account_status = 'ENABLED' AND v.is_open = true " +
        "AND (6371 * acos(cos(radians(:lat)) * cos(radians(v.lat)) * cos(radians(v.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(v.lat)))) <= v.service_radius_km " +
        "ORDER BY distance_km ASC LIMIT :limit", nativeQuery = true)
    List<Object[]> findNearbyVendors(@Param("lat") double lat, @Param("lng") double lng, @Param("limit") int limit);
}