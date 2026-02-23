package com.gaslink.api.modules.admin;
import com.gaslink.api.modules.admin.dto.DashboardStatsDto;
import com.gaslink.api.modules.admin.dto.*;
import com.gaslink.api.modules.order.OrderRepository;
import com.gaslink.api.modules.user.UserRepository;
import com.gaslink.api.modules.user.UserService;
import com.gaslink.api.modules.vendor.VendorRepository;
import com.gaslink.api.shared.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service @RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;

    public DashboardStatsDto getDashboardStats() {
        long totalUsers = userRepository.count();
        long activeVendors = vendorRepository.findByAccountStatus(VendorAccountStatus.ENABLED).size();
        Instant startOfDay = Instant.now().truncatedTo(ChronoUnit.DAYS);
        long ordersToday = orderRepository.findAll().stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().isAfter(startOfDay)).count();
        BigDecimal revenueToday = orderRepository.findAll().stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().isAfter(startOfDay)
                          && o.getPaymentStatus() == PaymentStatus.PAID)
                .map(o -> o.getTotal() != null ? o.getTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long pendingVerifications = vendorRepository.findByVerificationStatus(VerificationStatus.PENDING).size();
        return DashboardStatsDto.builder().totalUsers(totalUsers).activeVendors(activeVendors)
                .ordersToday(ordersToday).revenueToday(revenueToday)
                .pendingVerifications(pendingVerifications).openComplaints(0).build();
    }

    public void performUserAction(AdminUserActionRequest req) {
        boolean active = "ACTIVATE".equalsIgnoreCase(req.getAction());
        userService.setUserStatus(req.getUserId(), active);
    }
}