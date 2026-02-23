package com.gaslink.api.modules.admin.dto;
import lombok.*;
import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DashboardStatsDto {
    private long totalUsers;
    private long activeVendors;
    private long ordersToday;
    private BigDecimal revenueToday;
    private long pendingVerifications;
    private long openComplaints;
}