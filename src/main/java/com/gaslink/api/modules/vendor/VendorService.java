package com.gaslink.api.modules.vendor;
import com.gaslink.api.modules.vendor.dto.*;
import com.gaslink.api.modules.user.UserRepository;
import com.gaslink.api.modules.user.User;
import com.gaslink.api.shared.enums.*;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    @Transactional
    public VendorProfileDto register(UUID userId, VendorRegistrationRequest req) {
        if (vendorRepository.existsById(userId)) throw new BusinessRuleException("Vendor profile already exists");
        if (vendorRepository.existsByNin(req.getNin())) throw new BusinessRuleException("NIN already registered with another vendor");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(UserRole.VENDOR);
        userRepository.save(user);
        Vendor vendor = Vendor.builder().id(userId).businessName(req.getBusinessName())
                .businessAddress(req.getBusinessAddress()).nin(req.getNin())
                .lat(req.getLat()).lng(req.getLng()).serviceRadiusKm(req.getServiceRadiusKm()).build();
        return toDto(vendorRepository.save(vendor));
    }

    public VendorProfileDto getProfile(UUID vendorId) {
        return toDto(vendorRepository.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor not found")));
    }

    @Transactional
    public VendorProfileDto updateVerificationStatus(UUID vendorId, UpdateVendorStatusRequest req) {
        Vendor v = vendorRepository.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        v.setVerificationStatus(req.getVerificationStatus());
        return toDto(vendorRepository.save(v));
    }

    @Transactional
    public VendorProfileDto disableVendor(UUID vendorId, String reason) {
        Vendor v = vendorRepository.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        v.setAccountStatus(VendorAccountStatus.DISABLED);
        v.setAccountDisabledReason(reason);
        v.setOpen(false);
        return toDto(vendorRepository.save(v));
    }

    @Transactional
    public VendorProfileDto enableVendor(UUID vendorId) {
        Vendor v = vendorRepository.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        v.setAccountStatus(VendorAccountStatus.ENABLED);
        v.setAccountDisabledReason(null);
        return toDto(vendorRepository.save(v));
    }

    public List<NearbyVendorDto> getNearbyVendors(double lat, double lng, int limit) {
        return vendorRepository.findNearbyVendors(lat, lng, limit).stream().map(r ->
            NearbyVendorDto.builder()
                .id(UUID.fromString(r[0].toString()))
                .businessName(r[1] != null ? r[1].toString() : "")
                .lat(r[4] != null ? Double.parseDouble(r[4].toString()) : null)
                .lng(r[5] != null ? Double.parseDouble(r[5].toString()) : null)
                .rating(r[10] != null ? new BigDecimal(r[10].toString()) : BigDecimal.ZERO)
                .totalReviews(r[11] != null ? Integer.parseInt(r[11].toString()) : 0)
                .distanceKm(r[16] != null ? Double.parseDouble(r[16].toString()) : 0)
                .isOpen(true).build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public VendorProfileDto toggleOpen(UUID vendorId, boolean open) {
        Vendor v = vendorRepository.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        if (v.getAccountStatus() == VendorAccountStatus.DISABLED) throw new ForbiddenException("Your account is disabled.");
        v.setOpen(open);
        return toDto(vendorRepository.save(v));
    }

    private VendorProfileDto toDto(Vendor v) {
        return VendorProfileDto.builder().id(v.getId()).businessName(v.getBusinessName())
                .businessAddress(v.getBusinessAddress()).nin(v.getNin())
                .lat(v.getLat()).lng(v.getLng()).serviceRadiusKm(v.getServiceRadiusKm())
                .verificationStatus(v.getVerificationStatus()).accountStatus(v.getAccountStatus())
                .accountDisabledReason(v.getAccountDisabledReason())
                .isOpen(v.isOpen()).rating(v.getRating()).totalReviews(v.getTotalReviews()).build();
    }
}