package com.gaslink.api.modules.review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    boolean existsByOrderId(UUID orderId);
    List<Review> findByVendorIdOrderByCreatedAtDesc(UUID vendorId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.vendorId = :vendorId")
    Double avgRatingByVendorId(UUID vendorId);
    long countByVendorId(UUID vendorId);
}