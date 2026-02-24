package com.gaslink.api.modules.review;
import com.gaslink.api.modules.review.dto.*;
import com.gaslink.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/v1/reviews")
//@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ReviewDto>> create(Authentication auth, @Valid @RequestBody CreateReviewRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(reviewService.createReview(UUID.fromString(auth.getName()), req)));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getVendorReviews(@PathVariable UUID vendorId) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getVendorReviews(vendorId)));
    }
}