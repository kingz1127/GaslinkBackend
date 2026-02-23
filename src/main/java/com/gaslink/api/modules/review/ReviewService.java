package com.gaslink.api.modules.review;
import com.gaslink.api.modules.review.dto.*;
import com.gaslink.api.modules.order.*;
import com.gaslink.api.modules.vendor.*;
import com.gaslink.api.shared.enums.OrderStatus;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final VendorRepository vendorRepository;

    @Transactional
    public ReviewDto createReview(UUID customerId, CreateReviewRequest req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (!order.getCustomerId().equals(customerId)) throw new ForbiddenException("Access denied");
        if (order.getStatus() != OrderStatus.COMPLETED) throw new BusinessRuleException("Can only review completed orders");
        if (reviewRepository.existsByOrderId(req.getOrderId())) throw new BusinessRuleException("Order already reviewed");

        Review review = Review.builder().orderId(req.getOrderId()).customerId(customerId)
                .vendorId(order.getVendorId()).rating(req.getRating()).comment(req.getComment()).build();
        review = reviewRepository.save(review);
        updateVendorRating(order.getVendorId());
        return toDto(review);
    }

    public List<ReviewDto> getVendorReviews(UUID vendorId) {
        return reviewRepository.findByVendorIdOrderByCreatedAtDesc(vendorId).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    private void updateVendorRating(UUID vendorId) {
        Double avg = reviewRepository.avgRatingByVendorId(vendorId);
        long count = reviewRepository.countByVendorId(vendorId);
        vendorRepository.findById(vendorId).ifPresent(v -> {
            v.setRating(avg != null ? BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            v.setTotalReviews((int) count);
            vendorRepository.save(v);
        });
    }

    private ReviewDto toDto(Review r) {
        return ReviewDto.builder().id(r.getId()).orderId(r.getOrderId()).customerId(r.getCustomerId())
                .vendorId(r.getVendorId()).rating(r.getRating()).comment(r.getComment()).createdAt(r.getCreatedAt()).build();
    }
}